package com.core.pattern.adapter;

/**
 * @author ke.long
 * @since 2019/7/18 16:30
 */
public class FishingBoatAdapter implements RowingBoat {

    private FishingBoat boat;

    public FishingBoatAdapter() {
        boat = new FishingBoat();
    }

    @Override
    public void row() {
        boat.sail();
    }
}
