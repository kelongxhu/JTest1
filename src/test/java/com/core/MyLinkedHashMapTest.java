package com.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ke.long
 * @since 2019/7/2 16:02
 */
public class MyLinkedHashMapTest {
    private static final int MAX_ENTRIES = 4;

    public static void main(String[] args) {
        LinkedHashMap lhm = new LinkedHashMap(MAX_ENTRIES, 0.75F, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size()>MAX_ENTRIES;
            }
        };
        lhm.put(0, "H");
        lhm.put(1, "E");
        lhm.get("H");
        lhm.put(2, "L");
        lhm.put(3, "L");
        lhm.put(4, "O");

        System.out.println("" + lhm);

    }
}
