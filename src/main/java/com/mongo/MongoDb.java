package com.mongo;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author kelong
 * @date 10/23/14
 */
public class MongoDb {
    private Mongo mg = null;
    private DB db;
    private DBCollection users;

    public MongoDb() {
        init();
    }

    public void init() {
        try {
            mg = new Mongo("localhost", 30017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        //获取temp DB；如果默认没有创建，mongodb会自动创建
        db = mg.getDB("test1");
        //获取users DBCollection；如果默认没有创建，mongodb会自动创建
        users = db.getCollection("users");
    }

    public int insert(DBObject dbObject) {
        return users.save(dbObject).getN();
    }

    public long count(){
        return users.count();
    }

    public void destory() {
        if (mg != null)
            mg.close();
        mg = null;
        db = null;
        users = null;
        System.gc();
    }


    public void insertLog(){
        for (int i = 100; i <= 1000; i++) {
            DBObject appLog = new BasicDBObject();
            appLog.put("name", i);
            appLog.put("status", i+"X");
            appLog.put("age", i);
            appLog.put("familly", "A");
            appLog.put("tel", "13980862512");
            insert(appLog);
        }

//        for (int i = 100; i <= 200; i++) {
//            DBObject appLog = new BasicDBObject();
//            appLog.put("appId", i);
//            appLog.put("updateStatus", 0);
//            appLog.put("installStatus", 0);
//            appLog.put("createDate",new Date());
//            insert(appLog);
//        }
    }

    public static void main(String[] args) {
        MongoDb mongoDb = new MongoDb();
        mongoDb.insertLog();

        System.out.println(mongoDb.count());

    }
}
