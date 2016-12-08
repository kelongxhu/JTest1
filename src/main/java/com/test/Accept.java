package com.test;

/**
 * @author kelong
 * @date 2/25/16
 */
public class Accept {
    public static void main(String[] args) {
        AcceptHanlder acceptHanlder=new AcceptHanlder();
        acceptHanlder.hand(new Put(),new Get());
    }
}
