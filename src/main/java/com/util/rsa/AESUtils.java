//package com.util.rsa;
//
//import sun.misc.BASE64Decoder;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * java 中 AES 加密
// * Cipher.getInstance("AES") = Cipher.getInstance("AES/ECB/PKCS5Padding")
// *
// * 本算法采用能和大多数语言如PHP等互通的方案
// */
//public class AESUtils {
//
//    public static String PASSWORD = "AES7654321!#@tcl";
//
//    private static String IV =  "123456781234567";
//
//    private static String ALGORITHM = "AES/CBC/NoPadding";
//
//    public static String encrypt(String data , String key) throws Exception {
//        try {
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            int blockSize = cipher.getBlockSize();
//
//            byte[] dataBytes = data.getBytes();
//            int plaintextLength = dataBytes.length;
//            if (plaintextLength % blockSize != 0) {
//                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
//            }
//
//            byte[] plaintext = new byte[plaintextLength];
//            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
//
//            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
//
//            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
//            byte[] encrypted = cipher.doFinal(plaintext);
//            return new sun.misc.BASE64Encoder().encode(encrypted);
//
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static String decrypt(String data , String key) throws Exception {
//        try
//        {
//            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
//
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
//
//            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
//
//            byte[] original = cipher.doFinal(encrypted1);
//            String originalString = new String(original);
//            if (originalString != null)
//                return originalString.trim();
//            return null;
//        }
//        catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        String content = "dddddddddddddddddd";
//        String password = PASSWORD;
//        //加密
//       System.out.println("加密前：" + content);
//
//        String str = encrypt(content, password);
//        System.out.println("加密后:"+str);
//        //解密
//
//        String decryptResult = decrypt(str,password);
//        System.out.println("解密后：" + decryptResult.trim());
//    }
//}
