package com.core.pattern.bridge;

/**
 * @author ke.long
 * @since 2019/8/16 16:06
 */
public class BridgeClient {
    public static void main(String[] args) {
        //宝马自动挡
        Transmission auto = new Auto();
        AbstractCar bmw = new BMWCar();
        bmw.setTransmission(auto);
        bmw.run();

        //奔驰手动挡
        Transmission manual = new Manual();
        AbstractCar benz = new BenZCar();
        benz.setTransmission(manual);
        benz.run();
    }
}
