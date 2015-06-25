package com.gson;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelong on 8/14/14.
 */
public class GsonTest2 {
    @Test
    public void test(){
        List<Long> list=new ArrayList<Long>();
        list.add(new Long(1));
        list.add(new Long(2));
        Gson gson = new Gson();
        String s2 = gson.toJson(list);
        System.out.println(s2);
    }
}
