package com.core.pattern.decorator;

/**
 * @author ke.long
 * @since 2019/6/21 15:26
 */
public class Milk extends Decorator {
    private String description = "加了牛奶！";
    private Beverage beverage = null;
    public Milk(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public String getDescription(){
        return beverage.getDescription()+"\n"+description;
    }
    @Override
    public double getPrice(){
        return beverage.getPrice()+20;	//20表示牛奶的价格
    }
}
