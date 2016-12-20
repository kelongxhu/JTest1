package com.pattern.template;

/**
 * @author codethink
 * @date 12/20/16 2:45 PM
 */
public class StrategyTest {

    public static void main(String[] args) {
        String exp = "8+8";
        AbstractCalculator cal = new Plus();
        int result = cal.calculate(exp, "\\+");
        System.out.println(result);
    }
}

