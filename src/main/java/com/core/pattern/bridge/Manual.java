package com.core.pattern.bridge;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/8/16 16:02
 */
@Slf4j
public class Manual extends Transmission {
    @Override
    public void gear() {
        log.info("Manual transmission");
    }
}
