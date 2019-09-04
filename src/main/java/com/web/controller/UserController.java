package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.dao.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ke.long
 * @since 2019/8/23 16:34
 */
@Slf4j
@RestController
public class UserController {
    @RequestMapping("/getUser")
    public User getUser() {
        User user = new User();
        user.setUsername("hello");
        log.info("call:{}", JSON.toJSONString(user));
        return user;
    }
}
