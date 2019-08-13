package com.core.pattern.adapter;

/**
 * @author ke.long
 * @since 2019/7/18 16:31
 */
public class AdapterClient {
    public static void main(String[] args) {
        Captain captain = new Captain(new FishingBoatAdapter());
        captain.row();
    }
}
