package com.core.pattern.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/8/16 15:57
 */
@Slf4j
public class BMWCar extends AbstractCar {
    @Override
    public void run() {
        gear.gear();
        log.info("BMW is running");
    }
}
