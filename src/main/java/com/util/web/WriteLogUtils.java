package com.util.web;

import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WriteLogUtils {

    private static final int threadPoolSize = 3;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);

    public static void addLog(String log, Logger logger) {
        threadPool.execute(new Work(log, logger));
    }

    public static class Work implements Runnable {
        private String log = null;
        private Logger logger = null;

        public Work(String log, Logger logger) {
            this.log = log;
            this.logger = logger;
        }

        public void run() {
            logger.info(log);
        }
    }
}
