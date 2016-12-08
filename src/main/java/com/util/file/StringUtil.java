package com.util.file;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong.hou
 *         便于字符串拼接
 */
public class StringUtil {

    public static String append(Object... args) {
        StringBuffer buf = new StringBuffer();
        if (args != null && args.length > 0) {
            for (Object obj : args) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static boolean isNull(String... str) {
        boolean nil = true;
        if (null != str && str.length > 0) {
            for (String s : str) {
                // 只要有一个元素不为空，那么数组就不为空
                if (null != s && !"".equals(s.trim())) {
                    nil = false;
                    break;
                }
            }
        }
        return nil;
    }

    public static List<Long> parseList(String ids) {
        String[] idArr = ids.split(",");
        List<Long> idList = new ArrayList<Long>();
        if (idArr != null) {
            for (String id : idArr) {
                idList.add(new Long(id));
            }
        }
        return idList;
    }

    public static boolean isNullList(List list) {
        boolean flag = false;
        if (list == null || list.size() == 0) {
            flag = true;
        }
        return flag;
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

    /**
     * object to string
     *
     * @param object
     * @return
     */
    public static String obj2Str(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    /**
     * object to long
     *
     * @param object
     * @return
     */
    public static long obj2Long(Object object) {
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

    /**
     * object to float
     *
     * @param object
     * @return
     */
    public static float obj2Float(Object object) {
        if (object == null) {
            return 0;
        }
        float result = 0;
        try {
            result = Float.parseFloat(object.toString());
        } catch (NumberFormatException e) {
        }
        return result;
    }
}
