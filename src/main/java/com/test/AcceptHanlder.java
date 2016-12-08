package com.test;

/**
 * @author kelong
 * @date 2/25/16
 */
public class AcceptHanlder implements Hanlder<Put, Get> {

    @Override
    public void hand(Put put, Get get) {
        put.put();
        get.get();
    }
}
