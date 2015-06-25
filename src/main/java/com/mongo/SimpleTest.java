package com.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import java.net.UnknownHostException;

/**
 * @author kelong
 * @date 10/13/14
 */
public class SimpleTest {


    public static void main(String[] args) throws Exception {

        Mongo mg = new Mongo("localhost",10001);

        //查询所有的Database

        for (String name : mg.getDatabaseNames()) {

            System.out.println("dbName: " + name);

        }


        DB db = mg.getDB("test");

        //查询所有的聚集集合

        for (String name : db.getCollectionNames()) {

            System.out.println("collectionName: " + name);

        }


        DBCollection users = db.getCollection("users");


        //查询所有的数据

        DBCursor cur = users.find();

        while (cur.hasNext()) {

            System.out.println(cur.next());

        }

        System.out.println(cur.count());

        System.out.println(cur.getCursorId());

        System.out.println(JSON.serialize(cur));

    }

}
