package com.util;

public class StringUtils {

    public static String append(Object... args) {
        StringBuffer buf = new StringBuffer();
        if (args != null && args.length > 0) {
            for (Object obj : args) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static int obj2int(Object object) {
        if (object == null) {
            return 0;
        }
        int result = 0;
        try {
            result = Integer.parseInt(object.toString());
        } catch (NumberFormatException e) {
        }
        return result;
    }

    public static long obj2long(Object object) {
        if (object == null) {
            return 0;
        }
        long result = 0;
        try {
            result = Long.parseLong(object.toString());
        } catch (NumberFormatException e) {
        }
        return result;
    }


    public static String obj2Str(Object object) {
        return object == null ? "" : object.toString();
    }

    public static String format(int id) {
        return String.format("%09d", id);
    }
}
