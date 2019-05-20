package com.mock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/5/8 10:38
 */
@Slf4j
public class PasswordValidator {
    public boolean verifyPassword(String password) {
        log.info("verify,{}", password);
        return true;
    }
}
