package com.module.mongo;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.File;
import java.util.Date;
/**
 * @author kelong
 * @date 10/13/14
 */

public class MongoDBClientTest {

    public static void main(String[] args) {
//		initData();
//		query();
        initData4GridFS();
    }


    private static void initData4GridFS()   {
        long start = new Date().getTime();
        try {
            Mongo db = new Mongo("127.0.0.1", 10001);
            DB mydb = db.getDB("temp");
            File f = new File("/data/work/spring-servlet.xml");
            GridFS myFS = new GridFS(mydb);
            GridFSInputFile inputFile = myFS.createFile(f);
            inputFile.save();
            DBCursor cursor = myFS.getFileList();
            while(cursor.hasNext()){
                System.out.println(cursor.next());
            }
            db.close();
            long endTime = new Date().getTime();
            System.out.println(endTime - start);
            System.out.println((endTime - start) / 10000000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
