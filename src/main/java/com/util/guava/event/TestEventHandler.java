package com.util.guava.event;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TestEventHandler extends EventAdapter<TestEvent>{

    private static final Logger logger = LoggerFactory.getLogger(EventBusFacade.class);

    @Override
    public boolean process(TestEvent e) {

        logger.info("==================== 收到测试事件 ===================,{}", JSON.toJSONString(e));

        return true;
    }

}
