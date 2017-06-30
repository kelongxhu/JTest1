package com;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author kelong
 * @since 2017/6/30 10:35
 */
public class EhcacheTest {

    private Logger log=LoggerFactory.getLogger(getClass());

    @Test
    public void test(){
        // Create a cache manager
        final CacheManager cacheManager = new CacheManager();

        // create the cache called "helloworld"
        final Cache cache = cacheManager.getCache("helloworld");

        // create a key to map the data to
        final String key = "greeting";

        // Create a data element
        final Element putGreeting = new Element(key, "Hello, World!");

        // Put the element into the data store
        cache.put(putGreeting);

        // Retrieve the data element
        final Element getGreeting = cache.get(key);

        // Print the value
        System.out.println(getGreeting.getObjectValue());
    }

    @Test
    public void operationTest(){
        CacheManager manager = CacheManager.newInstance("src/test/resources/ehcache/ehcache.xml");

        // 获得Cache的引用
        Cache cache = manager.getCache("userCache");

        // 将一个Element添加到Cache
        cache.put(new Element("key1", "value1"));

        // 获取Element，Element类支持序列化，所以下面两种方法都可以用
        Element element1 = cache.get("key1");
        // 获取非序列化的值
        log.debug("key:{}, value:{}", element1.getObjectKey(), element1.getObjectValue());
        // 获取序列化的值
        log.debug("key:{}, value:{}", element1.getKey(), element1.getValue());

        // 更新Cache中的Element
        cache.put(new Element("key1", "value2"));
        Element element2 = cache.get("key1");
        log.debug("key:{}, value:{}", element2.getObjectKey(), element2.getObjectValue());

        // 获取Cache的元素数
        log.debug("cache size:{}", cache.getSize());

        // 获取MemoryStore的元素数
        log.debug("MemoryStoreSize:{}", cache.getMemoryStoreSize());

        // 获取DiskStore的元素数
        log.debug("DiskStoreSize:{}", cache.getDiskStoreSize());

        // 移除Element
        cache.remove("key1");
        log.debug("cache size:{}", cache.getSize());

        // 关闭当前CacheManager对象
        manager.shutdown();

        // 关闭CacheManager单例实例
        CacheManager.getInstance().shutdown();
    }


    @Cacheable(value = "users", condition = "#user.getId() <= 2")
    public Object findUser(String userId) {
        return null;
    }

    @CachePut(value = "users", key = "#user.getId()")
    public void updateUser(String userId) {
    }

    @CacheEvict(value = "users")
    public void removeUser(String userId) {
    }
}
