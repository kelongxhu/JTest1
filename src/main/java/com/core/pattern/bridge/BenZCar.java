package com.core.pattern.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/8/16 16:05
 */
@Slf4j
public class BenZCar extends AbstractCar {
    @Override
    public void run() {
        gear.gear();
        log.info("BenZCar is running");
    }
}
