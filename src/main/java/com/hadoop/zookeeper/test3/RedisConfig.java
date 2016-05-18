package com.hadoop.zookeeper.test3;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kelong
 * @date 12/17/14
 */
public class RedisConfig {
    private String host;
    private int port;

    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public static List<RedisConfig> getConfig() {
        RedisConfig config1 = new RedisConfig("127.0.0.1", 6379);
        RedisConfig config2 = new RedisConfig("127.0.0.1", 6389);
        List<RedisConfig> configs = new ArrayList<RedisConfig>();
        configs.add(config1);
        configs.add(config2);
        return configs;
    }

    public boolean alived() {
        try {
            Jedis jedis = new Jedis(host, port);
            String reply = jedis.ping();
            if ("PONG".equals(reply)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }

    public byte[] getBytes()throws Exception{
        return this.toString().getBytes("utf-8");
    }
}
