package com.service;

import java.util.List;

/**
 *
 */
public interface CacheService {

    /**
     * 根据key,获取Redis缓存值(对象)
     *
     * @param key
     * @param valueClass
     * @return
     */
    <T> T getValue(String key, Class<T> valueClass);

    /**
     * 根据key, 获取Redis缓存值(列表)
     *
     * @param key
     * @param valueClass
     * @return
     */
    <T> List<T> getListValue(String key, Class<T> valueClass);

    /**
     * 设置Redis缓存值
     *
     * @param key
     * @param value
     * @param expire
     */
    void setValue(String key, String value, boolean expire);

    /**
     * 指定缓存失效时间
     *
     * @param key
     * @param value
     * @param timeout
     */
    void setValue(String key, String value, long timeout);


    /**
     * 据key,获取Redis缓存值(字符串)
     *
     * @param key
     * @return
     */
    String getValue(String key);

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    void delete(String key);

    /**
     * 判断缓存是否包含该key
     *
     * @param key
     * @return
     */
    boolean containsKey(String key);

    /**
     * 获取key的剩余时间
     *
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * Queue数据添加
     *
     * @param key
     * @param value
     * @return
     */
    Long rightPush(String key, String value);

    /**
     * range队列中的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<String> rangeFromQueue(String key, long start, long end);

    /**
     * 获取队列中元素数量
     * @param key
     * @return
     */
    Long sizeFromQueue(String key);

    /**
     * 移除并返回队头元素
     * @param key
     * @return
     */
    String leftPop(String key);


    /**
     * 自增减key
     * @param key
     * @return
     */
    Long increment(String key, long delta);


}
