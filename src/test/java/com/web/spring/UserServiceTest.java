package com.web.spring;

import com.BaseTest;
import com.web.spring.transaction.mq.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ke.long
 * @since 2019/3/26 14:23
 */
public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void createUserTest(){
      userService.createUser();
    }
}
