package com.dao;

import com.dao.entity.User;
import com.dao.mybatis.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by _think on 2017/3/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void findUsersTest(){
        Page<User> page = new Page<User>();
        page.setPageNo(2);
        List<User> users = userDao.findUsers(page);
        page.setResults(users);
        System.out.println(page);
    }
}
