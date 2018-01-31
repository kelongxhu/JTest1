package com;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author codethink
 * @date 5/12/16 1:35 PM
 */
public class GuavaTest {
    @Test
    public void test() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String s = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(s);
        System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));
        System.out.println(Joiner.on("-").join("2011", "08", "04"));
        Iterator<String> it=Splitter.on(',').split("a,b").iterator();
        while (it.hasNext()){
            System.out.print("="+it.next());
        }
    }

    @Test
    public void MultisetTest(){
        Multiset<String> set= LinkedHashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("b");
        set.setCount("a",8); //添加或删除指定元素使其在集合中的数量是count
        System.out.println(set.count("a")); //给定元素在Multiset中的计数
        System.out.println(JSON.toJSONString(set));
        System.out.println(set);
        System.out.println(set.size()); //所有元素计数的总和,包括重复元素
        System.out.println(set.elementSet().size()); //所有元素计数的总和,不包括重复元素
        set.clear(); //清空集合
        System.out.println(set);
    }

    @Test
    public void MultimapTest(){
        Multimap<String,Integer> map= HashMultimap.create(); //Multimap是把键映射到任意多个值的一般方式
        map.put("a",1); //key相同时不会覆盖原value
        map.put("a",2);
        map.put("a",3);
        System.out.println(map); //{a=[1, 2, 3]}
        System.out.println(map.get("a")); //返回的是集合
        System.out.println(map.size()); //返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println(map.keySet().size()); //返回不同key的个数
        Map<String,Collection<Integer>> mapView=map.asMap();
    }
    @Test
    public void biMapTest(){
        BiMap<String,String> biMap= HashBiMap.create();
        biMap.put("sina","sina.com");
        biMap.put("qq","qq.com");
        biMap.put("sina","sina.cn"); //会覆盖原来的value
       /*
         在BiMap中,如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常
         如果对特定值,你想要强制替换它的键，请使用 BiMap.forcePut(key, value)
        */
        //biMap.put("tecent","qq.com"); //抛出异常
        biMap.forcePut("tecent","qq.com"); //强制替换key
        System.out.println(biMap);
        System.out.println(biMap.inverse().get("sina.com")); //通过value找key
        System.out.println(biMap.inverse());
        System.out.println(biMap.inverse().inverse()==biMap); //true
    }

    @Test
    public void guavaCacheTest() throws Exception {
        LoadingCache<String, String> cahceBuilder = CacheBuilder
            .newBuilder()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    String strProValue = "hello " + key + "!";
                    return strProValue;
                }

            });

        System.out.println("jerry value:" + cahceBuilder.apply("jerry"));
        System.out.println("jerry value:" + cahceBuilder.get("jerry"));
        System.out.println("peida value:" + cahceBuilder.get("peida"));
        System.out.println("peida value:" + cahceBuilder.apply("peida"));
        System.out.println("lisa value:" + cahceBuilder.apply("lisa"));
        cahceBuilder.put("harry", "ssdded");
        System.out.println("harry value:" + cahceBuilder.get("harry"));
    }

    @Test
    public void collectionTest() {
        Set<String> wordsWithPrimeLength =
            ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(primes, wordsWithPrimeLength);
        // intersection包含"two", "three", "seven"
        //        ImmutableSet<String> result=intersection.immutableCopy();

        Sets.SetView<String> x = Sets.difference(wordsWithPrimeLength, primes);
        Iterator<String> ss = x.iterator();
        while (ss.hasNext()) {
            System.out.println("============"+ss.next());
        }
    }

    public String createGuava() {
        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(
                new CacheLoader<String, String>() {
                    public String load(String key) throws Exception {
                        return "";
                        //return createExpensiveGraph(key);
                    }
                });
        try {
            return graphs.get("");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }
}
