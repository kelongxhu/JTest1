package com.core.pattern.decorator;

/**
 * @author ke.long
 * @since 2019/6/21 15:23
 */
public class CoffeeBean1 implements Beverage {
    private String description = "选了第一种咖啡豆";

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return 50;
    }
}
