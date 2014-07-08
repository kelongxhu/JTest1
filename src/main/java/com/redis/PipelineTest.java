package com.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Created by kelong on 7/7/14.
 */
public class PipelineTest {

    /**
     * @param args
     */

    public static void main(String[] args) {


        int count = 10000;

        //不用管道测试
        long start = System.currentTimeMillis();

        withoutPipeline(count);

        long end = System.currentTimeMillis();

        System.out.println("withoutPipeline: " + (end - start));


        //用管道测试
        start = System.currentTimeMillis();

        usePipeline(count);

        end = System.currentTimeMillis();

        System.out.println("usePipeline: " + (end - start));


    }

    /**
     * 不用管道测试连接
     * @param count
     */
    private static void withoutPipeline(int count) {

        Jedis jr = null;

        try {

            jr = new Jedis("172.26.35.130", 6389);

            for (int i = 0; i < count; i++) {

                jr.incr("testKey1");

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (jr != null) {

                jr.disconnect();

            }

        }

        System.out.println("testKey1 ID:"+jr.get("testKey1"));

    }

    /**
     * 用管道测试连接
     * @param count
     */
    private static void usePipeline(int count) {

        Jedis jr = null;

        try {

            jr = new Jedis("172.26.35.130", 6389);

            Pipeline pl = jr.pipelined();

            for (int i = 0; i < count; i++) {

                pl.incr("testKey2");

            }

            pl.sync();

            System.out.println("testKey2 ID:"+jr.get("testKey2"));

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (jr != null) {

                jr.disconnect();

            }

        }

    }
}
