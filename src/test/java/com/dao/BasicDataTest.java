package com.dao;

import com.util.HbaseHelper;
import com.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * @author kelong
 * @date 1/4/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class BasicDataTest {
    @Test
    public void test() throws Exception {
        long user = 100000;
        int product = 100;
        long start = 1451830000000l;
        long end = 0;
        for (int i = 0; i < 1000; i++) {
            end = start + 30 * 1000;
            String rowKey = StringUtils.append(user, "_", product, "_", start);
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "product_code", String.valueOf(product));
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "device_type",  "idol4");
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "activity_type","walking");
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "start_time", String.valueOf(start));
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "end_time", String.valueOf(end));
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "total_calories", String.valueOf(1000));
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "total_duration", String.valueOf(30));
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "total_steps", String.valueOf(5));
            HbaseHelper.insertRecord("basic_data", rowKey, "data", "total_distance", String.valueOf(1));
            start = end;
            System.out.print("插入成功:"+rowKey);
        }



    }
}
