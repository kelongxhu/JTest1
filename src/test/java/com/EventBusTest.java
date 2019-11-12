package com;

import com.util.guava.event.EventBusFacade;
import com.util.guava.event.TestEvent;
import org.junit.Test;

/**
 * @Author: ke.long
 * @Date: 2019/11/12 11:42
 */
public class EventBusTest extends BaseTest {
    @Test
    public void testExecute() {
        EventBusFacade.post(new TestEvent()); //发布事件
    }
}
