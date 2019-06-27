package com.core.pattern.decorator;

/**
 * @author ke.long
 * @since 2019/6/21 15:24
 */
public class CoffeeBean2 implements Beverage {
    private String description = "第二种咖啡豆！";

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return 100;
    }
}
