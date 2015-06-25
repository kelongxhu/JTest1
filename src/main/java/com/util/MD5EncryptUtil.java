package com.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kelong on 9/9/14.
 */
public class MD5EncryptUtil {
    private static Logger logger = Logger.getLogger(MD5EncryptUtil.class);

    /**
     * @description 加密的算法
     * @param plain
     * @return
     */
    public static String encryption(String plain) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plain.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return re_md5;
    }

    public static void main(String[] args){
        System.out.println(MD5EncryptUtil.encryption("11223344"));
    }
}
