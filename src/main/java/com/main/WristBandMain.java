package com.main;

import com.dao.WristBandDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kelong
 * @date 1/22/15
 */
public class WristBandMain {
    public static void main(String[] args){
        ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");
        WristBandDao bandDao=(WristBandDao)context.getBean("wristBandDao");
        bandDao.put();
    }
}
