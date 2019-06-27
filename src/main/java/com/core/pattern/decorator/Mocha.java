package com.core.pattern.decorator;

/**
 * @author ke.long
 * @since 2019/6/21 15:29
 */
public class Mocha extends Decorator {
        private String description = "加了摩卡！";
        private Beverage beverage = null;
        public Mocha(Beverage beverage){
            this.beverage = beverage;
        }
        @Override
        public String getDescription(){
            return beverage.getDescription()+"\n"+description;
        }
        @Override
        public double getPrice(){
            return beverage.getPrice()+49;	//30表示摩卡的价格
        }
}
