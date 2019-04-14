package com.module.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @author kelong
 * @date 12/30/14
 */
public class FileCopyToLocal {
    public static void main(String[] args)throws Exception{
        String dst = "hdfs://192.168.56.99:9000/input2/copy_file1.txt";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        FSDataInputStream hdfsInStream = fs.open(new Path(dst));
        OutputStream out = new FileOutputStream("/data/work/copy_file1_hdfs.txt");
        byte[] ioBuffer = new byte[1024];
        int readLen = hdfsInStream.read(ioBuffer);
        while(-1 != readLen){
            out.write(ioBuffer, 0, readLen);
            readLen = hdfsInStream.read(ioBuffer);
        }
        out.close();
        hdfsInStream.close();
        fs.close();
    }
}
