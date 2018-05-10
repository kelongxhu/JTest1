package com.core.concurrent.callback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2018/5/10 9:37
 */
@Slf4j
public class ManageFields {

    private String result;

    public void doIt(String str) {
        result = str;
        TimeUtils timeUtils = new TimeUtils((bjTime) -> {
            result = str + bjTime;
            log.info("result:{}", result);
        });
        timeUtils.getBjTime();
    }

    public static void main(String[] args) {
        new ManageFields().doIt("hi");
    }

    public class TimeUtils {
        TimeListener timeListener;

        public TimeUtils(TimeListener timeListener) {
            this.timeListener = timeListener;
        }

        public void getBjTime() {
            new Thread(() -> {
                String bjTime = "hell";
                timeListener.returnTime(bjTime);
            }
            ).start();
        }
    }

    interface TimeListener {
        void returnTime(String bjTime);
    }
}
