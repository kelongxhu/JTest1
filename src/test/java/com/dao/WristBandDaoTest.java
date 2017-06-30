package com.dao;

import com.dao.AmoebaDao;
import com.dao.WristBandDao;
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
public class WristBandDaoTest {

    @Resource
    private WristBandDao wristBandDao;

    @Test
    public void test() {
        wristBandDao.put();
    }
}
