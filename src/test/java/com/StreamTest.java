package com;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.hadoop.io.DoubleWritable;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 2 * @Author: ke.long
 * 3 * @Date: 2019/9/12 0:18
 * 4
 */
@Slf4j
public class StreamTest {
    @Test
    public void testStream() {
        Stream.of(3, 6, 8, 4, 9).max(Comparator.comparingInt(Integer::intValue)).ifPresent(System.out::println);
        List<User> userList = Arrays.asList(new User(1L, "a", 20,BigDecimal.ONE,DateUtils.addDays(new Date(),1)),
                new User(2L, "b", 30,BigDecimal.TEN,new Date()),
                new User(3L, "b", 30,BigDecimal.TEN,null));
        List<Long> userIds = userList.stream().filter(user -> user.getAge() >= 1)
                .sorted(Comparator.comparing(User::getId).reversed()).map(User::getId).collect(Collectors.toList());
        System.out.println(userIds);

        List<User> userList2= userList.stream().sorted(Comparator.comparing(d->((User) d).getCreateTime(),
                Comparator.nullsFirst(Date::compareTo)).reversed()).collect(Collectors.toList());


        userList.sort((o1, o2) -> {
            if (o1.getCreateTime() == null || o2.getCreateTime() == null) {
                return -1;
            }
            return o1.getCreateTime().compareTo(o2.getCreateTime());
        });

        System.out.println(userList2);
    }


    public static int compareByNameThenAge(User lhs, User rhs) {
        if (lhs.name.equals(rhs.name)) {
            return lhs.age - rhs.age;
        } else {
            return lhs.name.compareTo(rhs.name);
        }
    }

    @Test
    public void testOption() {
        int length = Optional.ofNullable("").map(String::length).orElse(-1);
        //求和，有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        //
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
    }


    @Test
    public void testGroupingBy() {
        List<User> userList = Lists.newArrayList(new User(1L, "a", 2,BigDecimal.ONE,null),
                new User(2L, "a", 2,BigDecimal.TEN,null),
                new User(3L, "c", 2,BigDecimal.TEN,null),
                new User(4L, "d", 2,BigDecimal.TEN,null));
        Map<String, Long> map = userList.stream().collect(Collectors.groupingBy(User::getName, Collectors.counting()));
        log.info("map:{}",map);
        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(System.out::println);
        //累加求和
        Map<String, Integer> sumMap = userList.stream().collect
        (Collectors.groupingBy(User::getName, Collectors.summingInt(User::getAge)));
        log.info("sumMap:{}",sumMap);
        //分组
        Map<String, List<User>> groupMap =
                userList.stream().collect(Collectors.groupingBy(User::getName));
        log.info("groupMap:{}",groupMap);
        //guava
//        Map<String, User> groupMap2 = Maps.uniqueIndex(userList, User::getName);
//        log.info("groupMap2:{}",groupMap2);

        //key， value取对象某个属性
        Map<String, List<Integer>> groupMap3 =
                userList.stream().collect(Collectors.groupingBy(User::getName,
                        Collectors.mapping(User::getAge, Collectors.toList())));
        log.info("group3:{}",groupMap3);

        Map<String,BigDecimal> collectMap=userList.stream().collect(
                Collectors.groupingBy(User::getName,
                Collectors.mapping(User::getValue, Collectors.reducing(BigDecimal.ZERO,BigDecimal::add))));

        log.info("collectMap:{}",collectMap);

        User user=new User(2L, "a", 2,BigDecimal.TEN,null);
        String name = Optional.ofNullable(user).map(l -> l.getName()).orElse("b");
        log.info("name:{}", name);

        //Optional.ofNullable(user)
        //    .ifPresent(u->{
        //        dosomething(u);
        //});
    }


    abstract class Sport {
        abstract void doSport(String name);
    }

    @Data
    @AllArgsConstructor
    private class User {
        private Long id;
        private String name;
        private Integer age;
        private BigDecimal value;
        //        public void win(T t) {
//            t.doSport("basketball");
//        }

        private Date createTime;

    }
}
