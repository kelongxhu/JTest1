package com.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.*;

/**
 * Created by kelong on 8/14/14.
 */
public class GsonTest {
    @Test
    public void objTest(){
        Gson gson = new Gson();

        Student student1 = new Student();
        student1.setId(1);
        student1.setName("李坤");
        student1.setBirthDay(new Date());

        // //////////////////////////////////////////////////////////
        System.out.println("----------简单对象之间的转化-------------");
        // 简单的bean转为json
        String s1 = gson.toJson(student1);
        System.out.println("简单Bean转化为Json===" + s1);

        // json转为简单Bean
        Student student = gson.fromJson(s1, Student.class);
        System.out.println("Json转为简单Bean===" + student);


        /////////////////////////////////LIST转换

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("曹贵生");
        student2.setBirthDay(new Date());

        Student student3 = new Student();
        student3.setId(3);
        student3.setName("柳波");
        student3.setBirthDay(new Date());

        List<Student> list = new ArrayList<Student>();
        list.add(student1);
        list.add(student2);
        list.add(student3);

        System.out.println("----------带泛型的List之间的转化-------------");
        // 带泛型的list转化为json
        String s2 = gson.toJson(list);
        System.out.println("带泛型的list转化为json==" + s2);

        // json转为带泛型的list
        List<Student> retList = gson.fromJson(s2,
                new TypeToken<List<Student>>() {
                }.getType());
        for (Student stu : retList) {
            System.out.println(stu);
        }
    }

    @Test
    public void test2(){
//注意这里的Gson的构建方式为GsonBuilder,区别于test1中的Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
                .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
                .setPrettyPrinting() //对json结果格式化.
                .setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
                        //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
                        //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
                .create();



        Student student1 = new Student();
        student1.setId(1);
        student1.setName("李坤");
        student1.setBirthDay(new Date());

        // //////////////////////////////////////////////////////////
        System.out.println("----------简单对象之间的转化-------------");
        // 简单的bean转为json
        String s1 = gson.toJson(student1);
        System.out.println("简单Bean转化为Json===" + s1);

        // json转为简单Bean
        Student student = gson.fromJson(s1, Student.class);
        System.out.println("Json转为简单Bean===" + student);
        // //////////////////////////////////////////////////////////

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("曹贵生");
        student2.setBirthDay(new Date());

        Student student3 = new Student();
        student3.setId(3);
        student3.setName("柳波");
        student3.setBirthDay(new Date());

        List<Student> list = new ArrayList<Student>();
        list.add(student1);
        list.add(student2);
        list.add(student3);

        System.out.println("----------带泛型的List之间的转化-------------");
        // 带泛型的list转化为json
        String s2 = gson.toJson(list);
        System.out.println("带泛型的list转化为json==" + s2);

        // json转为带泛型的list
        List<Student> retList = gson.fromJson(s2,
                new TypeToken<List<Student>>() {
                }.getType());
        for (Student stu : retList) {
            System.out.println(stu);
        }

    }

    @Test
    public void test3(){
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .create();

        Map<Point, String> map1 = new LinkedHashMap<Point, String>();// 使用LinkedHashMap将结果按先进先出顺序排列
        map1.put(new Point(5, 6), "a");
        map1.put(new Point(8, 8), "b");
        String s = gson.toJson(map1);
        System.out.println(s);// 结果:[[{"x":5,"y":6},"a"],[{"x":8,"y":8},"b"]]

        Map<Point, String> retMap = gson.fromJson(s,
                new TypeToken<Map<Point, String>>() {
                }.getType());
        for (Point p : retMap.keySet()) {
            System.out.println("key:" + p + " values:" + retMap.get(p));
        }
        System.out.println(retMap);

        System.out.println("----------------------------------");
        Map<String, Point> map2 = new LinkedHashMap<String, Point>();
        map2.put("a", new Point(3, 4));
        map2.put("b", new Point(5, 6));
        String s2 = gson.toJson(map2);
        System.out.println(s2);

        Map<String, Point> retMap2 = gson.fromJson(s2,
                new TypeToken<Map<String, Point>>() {
                }.getType());
        for (String key : retMap2.keySet()) {
            System.out.println("key:" + key + " values:" + retMap2.get(key));
        }

    }
}
