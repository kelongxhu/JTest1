package com.core.pattern.bridge;

/**
 * 抽象车
 * @author ke.long
 * @since 2019/8/16 15:57
 */
public abstract class AbstractCar {
    /**
     * 变速器
     */
    protected Transmission gear;

    public abstract void run();

    public void setTransmission(Transmission gear) {
        this.gear = gear;
    }

}
