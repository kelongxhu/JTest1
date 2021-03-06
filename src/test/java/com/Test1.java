package com;

import com.alibaba.fastjson.JSON;
import com.dao.entity.User;
import com.dao.entity.User2;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.redis.A;
import com.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kelong on 7/10/14.
 */
@Slf4j
public class Test1 {
    @Before
    public void before() {
    }

    @Test
    public void test1() {
        A a = new A();
        //        System.out.println("======:"+"test".hashCode());
        //        assertEquals(5, a.add(2, 3));

        String s =
                "58.215.204.118 - - [18/Sep/2013:06:51:35 +0000] \"GET /nodejs-socketio-chat/ HTTP/1.1\" 200 10818 \"http://www.google"
                        + ".com/url?sa=t&rct=j&q=nodejs%20%E5%BC%82%E6%AD%A5%E5%B9%BF%E6%92%AD&source=web&cd=1&cad=rja&ved=0CCgQFjAA&url"
                        + "=%68%74%74%70%3a%2f%2f%62%6c%6f%67%2e%66%65%6e%73%2e%6d%65%2f%6e%6f%64%65%6a%73%2d%73%6f%63%6b%65%74%69%6f%2d"
                        + "%63%68%61%74%2f&ei=rko5UrylAefOiAe7_IGQBw&usg=AFQjCNG6YWoZsJ_bSj8kTnMHcH51hYQkAA&bvm=bv.52288139,d.aGc\" "
                        + "\"Mozilla/5.0 (Windows NT 5.1; rv:23.0) Gecko/20100101 Firefox/23.0\"";
        String[] arr = s.split(" ");
        for (String i : arr) {
            System.out.println("value:" + i);
        }

        System.out.println("UUID:" + UUID.randomUUID().toString().replaceAll("-", ""));

        System.out.println(isContainChinese("china123是"));
    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Test
    public void shardTest() {
        handle("tableName", "dataSourceKey", 100, 2, 10);
    }

    /**
     * 分库分表获取DB,TABLE位置
     *
     * @param tableName
     * @param dataSourceKey
     * @param tableShardNum
     * @param dbShardNum
     * @param shardValue
     */
    private void handle(String tableName, String dataSourceKey, int tableShardNum,
                        int dbShardNum, Object shardValue) {
        long shard_value = Long.valueOf(shardValue.toString());
        long tablePosition = shard_value % tableShardNum;
        long dbPosition = tablePosition / (tableShardNum / dbShardNum);
        String finalTableName =
                new StringBuilder().append(tableName).append("_").append(tablePosition).toString();
        String finalDataSourceKey =
                new StringBuilder().append(dataSourceKey).append(dbPosition).toString();

        System.out.println(finalTableName);
        System.out.println(finalDataSourceKey);
    }

    @Test
    public void copyTest() {
        User user = new User();
        user.setUsername("test");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            User2 user2 = new User2();
            //BeanCopierUtils.copyProperties(user, user2); //69
            BeanUtils.copyProperties(user,user2); //414
        }
        System.out.println("costs:" + (System.currentTimeMillis() - start));
        //System.out.println(JSON.toJSONString(user));
        //System.out.println(JSON.toJSONString(user2));
        System.out.println(1001/1000);
        //0-999
        //1000-1999
        //2000-2500

    }

    @Test
    public void testStopwatch(){
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        // 开始计量时间
        stopwatch.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 停止计量时间
        stopwatch.stop();
        // 根据输入时间单位获取相应的时间
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));

    }

    @Test
    public void test2(){
        char c = 'x';
        System.out.println(true?120:c);
        //case2
        int[] arr = new int[]{1,2,3};
        boolean flag = Arrays.asList(arr).contains(1);
        System.out.println(flag);
        System.out.println(Arrays.asList(arr).size());
        //case3
        System.out.println(isOdd(0));
        //case
        boolean flag2 = Boolean.getBoolean("true");
        System.out.println(flag2);
        System.out.println(Boolean.parseBoolean("true"));


        System.out.println();

        AtomicInteger cycleCount = new AtomicInteger(0);

        for (int i=0;i<10;i++){
            int cycleNo = cycleCount.addAndGet(1);
            System.out.println(cycleNo/2);
            System.out.println(cycleNo/2<=1);
        }
    }

    public static boolean isOdd(int i){
        return i%2 == 1;
    }


    @Test
    public void testLog4j(){
        StringBuffer sb=new StringBuffer("aaaaaaaaaaa");
        sb.setLength(0);
        System.out.println("sb:"+sb.toString());
        sb.append("xxxxx");
        System.out.println("sb:"+sb.toString());
        System.out.println("sb:"+sb.capacity());
    }

    @Test
    public void test3(){
        //String s0="zxy";
        String s1=new String("z")+new String("xy");
        System.out.println(s1==s1.intern());

        String str3 = new String("2") + new String("2");
        str3.intern();  //常量池存储的str3引用
        String str4 = "22";//  什么都不做， str4返回str3引用
        System.out.println(str3 == str4);
    }


    @Test
    public void test4()throws Exception{
        Thread thread=new Thread(()->{
            try {
                System.out.println("线程启动");
                Thread.sleep(10000);
                System.out.println("hello");
            } catch (InterruptedException e) {
                System.out.println("线程中断");
                System.out.println("线程中断状态："+Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
            }
            System.out.println("线程中断状态："+Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
        Thread.sleep(1000000000);//
    }

    @Test
    public void test5(){
        List<Integer> a= ImmutableList.of(1,2);
        List<Integer> b=ImmutableList.copyOf(a);
        List<Integer> c=ImmutableList.copyOf(a);
        System.out.println(JSON.toJSONString(c));

        ThreadLocal<Integer> tl=new ThreadLocal<>();
        tl.set(5);
        ThreadLocal<String> tl2=new ThreadLocal<>();
        tl2.set("6");
        String value=tl2.get();
        System.out.println(value);

    }

    @Test
    public void fileTest()throws Exception{
        String urlStrEncode="_%E8%B5%84%E9%87%91%E8%87%AA%E8%90%A5%E5%8F%B0%E8%B4%A6.xlsx_";
        String urlStrDecode = URLDecoder.decode(urlStrEncode, "utf-8");
        System.out.println(urlStrDecode);
        System.out.println(org.apache.commons.lang3.StringUtils.substring(urlStrDecode,0,5));
    }

}
