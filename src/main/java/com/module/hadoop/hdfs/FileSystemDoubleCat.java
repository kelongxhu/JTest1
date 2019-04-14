package com.module.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.net.URI;

/**
 * Created by kelong on 7/17/14.
 */
public class FileSystemDoubleCat {
    public static void main(String[] args) throws Exception {
        String uri = "hdfs://192.168.56.99:9000/input/file2.txt";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        FSDataInputStream in=null;
        try {
            in = fs.open(new Path(uri));
            IOUtils.copyBytes(in, System.out, 4096, false);
            in.seek(0);
            IOUtils.copyBytes(in, System.out, 4096, false);
        }   finally {
            IOUtils.closeStream(in);
        }
    }
}
