package com.util.gson.fastjson;

import org.junit.Test;
import com.alibaba.fastjson.JSON;

/**
 * Created by kelong on 8/18/14.
 */
public class FastJsonTest {
    @Test
    public void simpleTest() {
        Person person=new Person("lili",27);
        String jsonString = JSON.toJSONString(person);
        System.out.println("jsonString:"+jsonString);
        Person person1 =JSON.parseObject(jsonString,Person.class);
        System.out.println("object:"+person1.getAge());
    }
}
