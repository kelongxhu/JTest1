package com.dao;

import com.dao.AmoebaDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author kelong
 * @date 12/9/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class AmoebaDaoTest {

    @Resource
    private AmoebaDao amoebaDao;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            amoebaDao.amoebaTest();
        }
    }

    @Test
    public void insert() {
        for (int i = 0; i < 2; i++) {
            amoebaDao.amoebaInsertTest();
        }
    }

    @Test
    public void test2(){
        System.out.println("sssss");
    }
}
