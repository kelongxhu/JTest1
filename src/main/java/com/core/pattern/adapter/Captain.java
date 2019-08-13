package com.core.pattern.adapter;

/**
 * @author ke.long
 * @since 2019/7/18 16:29
 */
public class Captain implements RowingBoat {
    private RowingBoat rowingBoat;

    public Captain(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }
    @Override
    public void row() {
        rowingBoat.row();
    }
}
