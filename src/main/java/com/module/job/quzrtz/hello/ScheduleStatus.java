package com.module.job.quzrtz.hello;

/**
 * @author codethink
 * @date 12/21/16 4:28 PM
 */
public enum ScheduleStatus {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 暂停
     */
    PAUSE(1);

    private int value;

    private ScheduleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
