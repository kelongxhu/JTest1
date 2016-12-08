package com.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by kelong on 7/10/14.
 */
public class A {
    private static final Log LOG = LogFactory.getLog(A.class);
    public int add(int x,int y){
        LOG.debug("debug+++++++");
        return x+y;
    }

    public static void main(String[] args){
        try {
            A a=new A();
            int x=a.add(2,3);
            LOG.info("x:"+x);
           int[] b=new int[1];
            b[0]=2;
            LOG.info("b[0]"+b[0]);
            LOG.info("b[1]"+b[1]);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage(),e);
        }
        // LOG.info(s.toString());
    }
}
