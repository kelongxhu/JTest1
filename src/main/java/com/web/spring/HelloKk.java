package com.web.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ke.long
 * @since 2019/3/22 15:56
 */
@Slf4j
@Service
public class HelloKk implements BaseInterface {
    @Override
    public void init() {
        log.info("hello,kk!");
    }
}
