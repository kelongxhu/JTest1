package com.core.pattern.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * 自动挡
 * @author ke.long
 * @since 2019/8/16 16:03
 */
@Slf4j
public class Auto extends Transmission {
    @Override
    public void gear() {
        log.info("Auto transmission");
    }
}
