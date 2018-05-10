package com.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ke.long
 * @since 2018/2/2 11:02
 */
public class BuildSqlTest {
    @Test
    public void buildSQL() {
        List<String> sqlData = new ArrayList<>();
        List<String> shopIds = new ArrayList<>();

        try {
            File csv = new File("C:\\Users\\kelong\\Desktop\\1111.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            String itemUpdate = "";
            Joiner joiner = Joiner.on(",");
            StringBuilder sb=new StringBuilder();
            while ((line = br.readLine()) != null) {    //判断文件是否结束
                String item[] = line.split(",");      //csv 逗号分隔
                //String item[] = line.split("\t");
                for (String s : item) {
                    joiner.appendTo(sb,"'", s, "'");
                }
            }
            System.out.println(sb.toString());

            //FileWriter fw = null;
            //BufferedWriter bw = null;
            //try {
            //    fw = new FileWriter("C:\\Users\\kelong\\Desktop\\112222.txt", true);
            //    bw = new BufferedWriter(fw, 100);
            //    for (String string : sqlData) {
            //        bw.write(string);
            //
            //    }
            //} catch (IOException e) {
            //    System.out.println("写入文件出错");
            //} finally {
            //    if (bw != null) {
            //        bw.flush();
            //        bw.close();
            //    }
            //    if (fw != null) { fw.close(); }
            //}
            //br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
