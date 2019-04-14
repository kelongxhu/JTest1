package com.web.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author ke.long
 * @since 2019/3/26 9:36
 */
@Slf4j
@Service
public class SimpleInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init,hello world???");
    }
}
