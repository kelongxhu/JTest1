package com;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.BiMap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

/**
 * @author codethink
 * @date 5/12/16 1:35 PM
 */
@Slf4j
public class GuavaTest {
    @Test
    public void test() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String s = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(s);
        System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));
        System.out.println(Joiner.on("-").join("2011", "08", "04"));
        Iterator<String> it = Splitter.on(',').split("a,b").iterator();
        while (it.hasNext()) {
            System.out.print("=" + it.next());
        }
    }

    @Test
    public void MultisetTest() {
        Multiset<String> set = LinkedHashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("b");
        set.setCount("a", 8); //添加或删除指定元素使其在集合中的数量是count
        System.out.println(set.count("a")); //给定元素在Multiset中的计数
        System.out.println(JSON.toJSONString(set));
        System.out.println(set);
        System.out.println(set.size()); //所有元素计数的总和,包括重复元素
        System.out.println(set.elementSet().size()); //所有元素计数的总和,不包括重复元素
        set.clear(); //清空集合
        System.out.println(set);
    }

    @Test
    public void MultimapTest() {
        Multimap<String, Integer> map = HashMultimap.create(); //Multimap是把键映射到任意多个值的一般方式
        map.put("a", 1); //key相同时不会覆盖原value
        map.put("a", 2);
        map.put("a", 3);
        System.out.println(map); //{a=[1, 2, 3]}
        System.out.println(map.get("a")); //返回的是集合
        System.out.println(map.size()); //返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println(map.keySet().size()); //返回不同key的个数
        Map<String, Collection<Integer>> mapView = map.asMap();
    }

    @Test
    public void biMapTest() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("sina", "sina.com");
        biMap.put("qq", "qq.com");
        biMap.put("sina", "sina.cn"); //会覆盖原来的value
       /*
         在BiMap中,如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常
         如果对特定值,你想要强制替换它的键，请使用 BiMap.forcePut(key, value)
        */
        //biMap.put("tecent","qq.com"); //抛出异常
        biMap.forcePut("tecent", "qq.com"); //强制替换key
        System.out.println(biMap);
        System.out.println(biMap.inverse().get("sina.com")); //通过value找key
        System.out.println(biMap.inverse());
        System.out.println(biMap.inverse().inverse() == biMap); //true
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
            System.out.println("============" + ss.next());
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

    @Test
    public void table() {
        //新建一个table对象，并存入数据
        Table<String, String, Integer> tables = HashBasedTable.create();
        tables.put("a", "javase", 80);
        tables.put("b", "javaee", 90);
        tables.put("c", "javame", 100);
        tables.put("d", "guava", 70);

        System.out.println("---------------------------按学生查看成绩---------------------------");

        System.out.print("学生\t");
        //得到所有课程
        Set<String> courses = tables.columnKeySet();
        for (String str : courses) {
            System.out.print(str + "\t");
        }

        System.out.println();
        //得到所有学生
        Set<String> stus = tables.rowKeySet();
        for (String str : stus) {
            System.out.print(str + "\t");
            //课程成绩表
            Map<String, Integer> scores = tables.row(str);
            for (String c : courses) {
                //输出成绩
                System.out.print(scores.get(c) + "\t");
            }
            System.out.println();
        }

        System.out.println("---------------------------按课程查看成绩---------------------------");

        System.out.print("课程\t");
        //得到所有学生
        Set<String> stus2 = tables.rowKeySet();
        for (String str : stus2) {
            System.out.print(str + "\t");
        }
        System.out.println();

        //得到所有课程
        Set<String> courses2 = tables.columnKeySet();
        for (String str : courses2) {
            System.out.print(str + "\t");
            //得到学生成绩表
            Map<String, Integer> map = tables.column(str);
            for (String stu : stus2) {
                //输出成绩
                System.out.print(map.get(stu) + "\t");
            }
            System.out.println();
        }

        System.out.println("---------------------------转换---------------------------");
        //将列调换，由学生-课程-成绩表变为 课程-学生-成绩
        Table<String, String, Integer> tables2 = Tables.transpose(tables);
        // 得到所有行数据
        Set<Cell<String, String, Integer>> cells2 = tables2.cellSet();
        for (Cell<String, String, Integer> temp : cells2) {
            System.out.println(temp.getRowKey() + " " + temp.getColumnKey() + " " + temp.getValue());
        }
    }

    @Test
    public void testPartitionList() {
        Map<String, String> map = Maps.newHashMap();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        map.put("5", "e");
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        List<String> values = Lists.partition(list, 2).stream().map(subInputs -> {
            return subInputs.stream().map(l -> map.get(String.valueOf(l))).collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(values);
    }

    @Test
    public void testStream() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("Apple", "Banana", "Carrot", "Doughnut");

        List<String> zipped = Streams.zip(streamA,
                streamB,
                (a, b) -> a + " is for " + b)
                .collect(Collectors.toList());
<<<<<<< HEAD

        log.info("{}", JSON.toJSONString(zipped));
=======
        log.info("{}",JSON.toJSONString(zipped));
>>>>>>> 4737680cf6d850ccc386a53bfe098c90da346bb4
    }

    @Test
    public void testMap() {
        Person p1 = new Person("001", "zhang_san");
        Person p2 = new Person("002", "li_si");
        Person p3 = new Person("002", "li_s4");

        List<Person> personList = Lists.newArrayList();
        personList.add(p1);
        personList.add(p2);

        // 将主键当作Map的Key
        Map<String, Person> personMap = Maps.uniqueIndex(personList.iterator(), new Function<Person, String>() {
            @Override
            public String apply(Person input) {
                return input.getId();
            }

        });
        System.out.println("将主键当作Map的Key:" + personMap);

        // 可以说是Maps.uniqueIndex相反的作用
        Set<Person> personSet = Sets.newHashSet(p1, p2);
        @SuppressWarnings("unused")
        Map<Person, String> personAsMap = Maps.asMap(personSet, new Function() {
            @Override
            public Object apply(Object input) {
                return ((Person) input).getId();
            }
        });
        System.out.println(personAsMap);

        // 转换Map中的value值
        Map<String, String> transformValuesMap = Maps.transformValues(personMap, v -> v.getName());
        System.out.println("转换Map中的value值" + transformValuesMap);

        //分组
        Map<String, Map<String, Person>> groupMap = personList.stream().
                collect(Collectors.groupingBy(Person::getId, Collectors.toMap(Person::getName, e -> e, (oldVal, newVal) -> newVal)));

        System.out.println(groupMap);

    }

    @Test
    public void testTransformValues() {
        ImmutableBiMap<String, String> map = ImmutableBiMap.of("read", "query,list", "write", "save,update");
        Map<String, List<String>> map2 = Maps.transformValues(map, entity -> Arrays.stream(entity.split(",")).
                filter(s -> StringUtils.isNotBlank(s)).collect(Collectors.toList()));
        log.info("{}", map2);

        String ss = map2.entrySet().stream().map(entry -> filter(entry)).filter(s -> StringUtils.isNotBlank(s)).findFirst().orElse("");

        log.info("2{}", ss);
    }

    private String filter(Map.Entry<String, List<String>> entry) {
        boolean flag = entry.getValue().stream().filter(e -> "2saveUser".startsWith(e)).findFirst().isPresent();
        return flag ? entry.getKey() : null;
    }

    class Person {
        private String Id;
        private String name;

        public Person(String Id, String name) {
            this.Id = Id;
            this.name = name;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void testFluentIterable() {
        Set set = Sets.newHashSet("a", "b");
        ImmutableListMultimap<String, String> groups = FluentIterable.from(set).index(l -> Joiner.on("#").join(l, "x"));
        log.info("{}", groups);
    }

    @Test
    public void testOptional() {
        log.info("{}", Optional.ofNullable("").orElse("aa"));

        int hash = Hashing.consistentHash(Hashing.murmur3_32().hashString("c3uTGRY15LtbeR¶6704", Charset.defaultCharset()), 100);
        String partitionTable = "cipher_" + Math.abs(hash) % 100;

        log.info("{}", partitionTable);

        log.info("{}", TimeUnit.MINUTES.toSeconds(1));

        Optional<Object> value = Optional.ofNullable("aaa");

        log.info(value.get().toString());

        String textWithSalt = "aa";
        byte[] hash2 = Hashing.sha256().hashString(textWithSalt, Charset.forName("utf-8")).asBytes();
        String base64 = BaseEncoding.base64().encode(hash2);
        log.info("{},{}", hash2, base64);

    }

    @Test
    public void testMapAndFlatMap() {
        List<String> words = new ArrayList<String>();
        words.add("hello");
        words.add("word");

        //将words数组中的元素再按照字符拆分，然后字符去重，最终达到["h", "e", "l", "o", "w", "r", "d"]
        //如果使用map，是达不到直接转化成List<String>的结果
        List<String> stringList = words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct()
                .collect(Collectors.toList());
        stringList.forEach(e -> System.out.println(e));

        List<Stream> s = words.stream().map(w -> Arrays.stream(w.split(""))).collect(Collectors.toList());
    }

    @Test
    public void testPeek() {
        List<String> nations = Arrays.asList("A", "B", "C", "A1");
        nations.stream().
                filter(nation -> {
                    return nation.startsWith("A");
                }).
                peek(nation -> System.out.println(nation)).
                map((t) -> {
                    return t + "a";
                }).
                peek(nation -> System.out.println(nation)).
                collect(Collectors.toList());
    }

    @Test
    public void stopWatch()throws Exception{
        // 创建stopwatch并开始计时
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1980);
        // 以秒打印从计时开始至现在的所用时间,向下取整
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1
        // 停止计时
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1
        // 再次计时
        stopwatch.start();
        Thread.sleep(100);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 2
        // 重置并开始
        stopwatch.reset().start();
        Thread.sleep(1030);
        // 检查是否运行
        System.out.println(stopwatch.isRunning()); // true
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS); // 1031
        System.out.println(millis);
        // 打印
        System.out.println(stopwatch.toString()); // 1.03 s
    }

}
