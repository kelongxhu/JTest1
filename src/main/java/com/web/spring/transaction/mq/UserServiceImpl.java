package com.web.spring.transaction.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ke.long
 * @since 2019/3/26 14:16
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AfterCommitExecutor afterCommitExecutor;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUser() {
        log.info("execute task");
        afterCommitExecutor.execute(() -> {
            log.info("after execute commit to do task...");
        });
    }
}
