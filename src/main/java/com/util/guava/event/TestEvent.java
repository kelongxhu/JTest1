package com.util.guava.event;

/**
 * @Author: ke.long
 * @Date: 2019/11/12 11:40
 */
public class TestEvent implements BaseEvent{

    private String name;

    public TestEvent(){}

    public TestEvent(String a){
        this.name = a;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
