//package com.util.rsa;
//
//
//import com.util.rsa.RSACoder;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Map;
//
///**
// * @author kelong
// * @date 11/12/14
// */
//public class RSACoderTest {
//    private String publicKey;
//    private String privateKey;
//    private String dataStr1 = "An ID issued by the GCM servers to the Android application that allows it to receive messages相当多的等等等xxx";
//    private String dataStr2 = dataStr1 + dataStr1;
//
//
//    public Map<String,Object> keyMap;
//
//    @Before
//    public void setUp() throws Exception {
//        long start1 = System.currentTimeMillis();
//        keyMap = RSACoder.initKey();
//        long start2 = System.currentTimeMillis();
//        publicKey = RSACoder.getPublicKey(keyMap);
//        long end1=System.currentTimeMillis();
//        privateKey = RSACoder.getPrivateKey(keyMap);
//        long end2=System.currentTimeMillis();
//        System.out.println("密钥对生成时间:"+(start2-start1));
//        System.err.println("公钥解析时间："+(end1-start2) +"====="+ publicKey);
//        System.err.println("私钥解析时间："+(end2-end1) +"======"+privateKey);
//
//    }
//
//    /**
//     * 加密耗时
//     *
//     * @throws Exception
//     */
//    @Test
//    public void encryptTest() throws Exception {
//        System.out.println("公钥加密——私钥解密耗时测试========");
//        long start = System.currentTimeMillis();
//        String inputStr = dataStr2;
//        byte[] data = inputStr.getBytes();
//        System.out.println("str大小:" + data.length);
//        //////加密
//        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
//
//        long enEnd = System.currentTimeMillis();
//        long time = enEnd - start;
//        System.out.println("加密耗时:" + time);
//
//        ////////解密：
//        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
//                privateKey);
//
//
//        String outputStr = new String(decodedData);
//
//        long deEnd = System.currentTimeMillis();
//        long detime = deEnd - enEnd;
//        System.err.println("解密耗时:" + detime);
//        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
//    }
//
//
//    /**
//     * 解密测试
//     *
//     * @param encodedData
//     * @return
//     * @throws Exception
//     */
//    public String deData(byte[] encodedData) throws Exception {
//        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
//                privateKey);
//        return new String(decodedData);
//    }
//
//
//    /**
//     * 加密测试
//     *
//     * @param inputStr
//     * @return
//     * @throws Exception
//     */
//    public byte[] enData(String inputStr) throws Exception {
//        byte[] data = inputStr.getBytes();
//        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
//        return encodedData;
//    }
//
//
//    @Test
//    public void forTest() throws Exception {
//        int times=1000;
//        String inputData = dataStr1;
//        byte[] decodedData = null;
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < times; i++) {
//            decodedData = enData(inputData);
//        }
//        long end = System.currentTimeMillis();
//
//        long total=end-start;
//        System.out.println(times+"次加密总耗时:"+total+",平均耗时:" + total*1.0 / times);
//
//        String output = "";
//        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < times; i++) {
//            output = deData(decodedData);
//        }
//        long end1 = System.currentTimeMillis();
//        long total1=end1-start1;
//        System.out.println(times+"次解密总耗时:"+total1+",平均耗时:" + total1*1.0 /times);
//        System.out.println("解密后：" + output);
//
//    }
//
//
//
//
//
//
//
//    /**
//     * 加密耗时2
//     *
//     * @throws Exception
//     */
//    @Test
//    public void encryptTest2() throws Exception {
//        System.out.println("私钥加密——公钥解密耗时测试========");
//        long start = System.currentTimeMillis();
//        String inputStr = dataStr2;
//        byte[] data = inputStr.getBytes();
//        System.out.println("str大小:" + data.length);
//        //////加密
//        byte[] encodedData = RSACoder.encryptByPrivateKey(data,privateKey);
//
//        long enEnd = System.currentTimeMillis();
//        long time = enEnd - start;
//        System.out.println("加密耗时:" + time);
//
//        ////////解密：
//        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData,
//                publicKey);
//
//
//        String outputStr = new String(decodedData);
//
//        long deEnd = System.currentTimeMillis();
//        long detime = deEnd - enEnd;
//        System.err.println("解密耗时:" + detime);
//        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
//    }
//
//
//
//
//    /**
//     * 加密测试
//     *
//     * @param inputStr
//     * @return
//     * @throws Exception
//     */
//    public byte[] enData2(String inputStr) throws Exception {
//        byte[] data = inputStr.getBytes();
//        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
//        return encodedData;
//    }
//
//    /**
//     * 解密测试
//     *
//     * @param encodedData
//     * @return
//     * @throws Exception
//     */
//    public String deData2(byte[] encodedData) throws Exception {
//        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData,
//                publicKey);
//        return new String(decodedData);
//    }
//
//
//
//    @Test
//    public void forTest2() throws Exception {
//        int times=1;
//        String inputData = dataStr1;
//        byte[] decodedData = null;
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < times; i++) {
//            decodedData = enData2(inputData);
//        }
//        long end = System.currentTimeMillis();
//
//        long total=end-start;
//        System.out.println(times+"次加密总耗时:"+total+",平均耗时:" + total*1.0 / times);
//
//        String output = "";
//        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < times; i++) {
//            output = deData2(decodedData);
//        }
//        long end1 = System.currentTimeMillis();
//        long total1=end1-start1;
//        System.out.println(times+"次解密总耗时:"+total1+",平均耗时:" + total1*1.0 /times);
//        System.out.println("解密后：" + output);
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
