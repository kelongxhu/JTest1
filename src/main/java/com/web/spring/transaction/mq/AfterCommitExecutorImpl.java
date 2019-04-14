package com.web.spring.transaction.mq;

/**
 * @author ke.long
 * @since 2019/3/26 14:01
 */

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * stolen from http://azagorneanu.blogspot.jp/2013/06/transaction-synchronization-callbacks.html
 */
@Component
@Slf4j
public class AfterCommitExecutorImpl extends TransactionSynchronizationAdapter implements AfterCommitExecutor {
    private static final ThreadLocal<List<Runnable>> RUNNABLES = new ThreadLocal<List<Runnable>>();

    private static int DEFAULT_MQ_PRODUCER_SEND_LIMIT_PER_SECOND = 5;

    @Override
    public void execute(Runnable runnable) {
        if(log.isInfoEnabled()) {
            log.info("Submitting new runnable {} to run after commit", runnable);
        }
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            if(log.isInfoEnabled()) {
                log.info("Transaction synchronization is NOT ACTIVE. Executing right now runnable {}", runnable);
            }
            runnable.run();
            return;
        }
        List<Runnable> threadRunnables = RUNNABLES.get();
        if (threadRunnables == null) {
            threadRunnables = new ArrayList<Runnable>();
            RUNNABLES.set(threadRunnables);
            TransactionSynchronizationManager.registerSynchronization(this);
        }
        threadRunnables.add(runnable);
    }

    @Override
    public void afterCommit() {
        List<Runnable> threadRunnables = RUNNABLES.get();
        //限制消息发送速率, 防止相同的消息在处理时产生冲突
        final RateLimiter rateLimiter = RateLimiter.create(getSendLimitPerSecond());
        if(log.isInfoEnabled()) {
            log.info("Transaction successfully committed, executing {} runnables", threadRunnables.size());
        }
        for (int i = 0; i < threadRunnables.size(); i++) {
            Runnable runnable = threadRunnables.get(i);
            if(log.isInfoEnabled()) {
                log.info("Executing runnable {}", runnable);
            }
            try {
                rateLimiter.acquire();
                runnable.run();
            } catch (RuntimeException e) {
                log.error("Failed to execute runnable " + runnable, e);
            }
        }
    }

    @Override
    public void afterCompletion(int status) {
        if(log.isInfoEnabled()) {
            log.info("Transaction completed with status {}", status == STATUS_COMMITTED ? "COMMITTED" : "ROLLED_BACK");
        }
        RUNNABLES.remove();
    }

    private double getSendLimitPerSecond() {
        return DEFAULT_MQ_PRODUCER_SEND_LIMIT_PER_SECOND;
    }
}
