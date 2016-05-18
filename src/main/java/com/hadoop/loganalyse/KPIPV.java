package com.hadoop.loganalyse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * Created by kelong on 8/5/14.
 */
public class KPIPV {
    private static final Log LOG = LogFactory.getLog(KPIIP.class);
    //map将输入中的value复制到输出数据的key上，并直接输出

    public static class Map extends Mapper<Object, Text, Text, IntWritable> {
        private static Text line = new Text();//每行数据

        private IntWritable one = new IntWritable(1);
        private Text word = new Text();

        //实现map函数
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            KPI kpi = KPI.filterPVs(value.toString());
            if(kpi.isValid()){
//                LOG.info("request:" + kpi.getRequest());
                if ("2014122407".equals(kpi.getTime_local_Date_hour())&&"/phone/unregister".equals(kpi.getRequest())) {
                    word.set(kpi.getRemote_addr());
                    context.write(word, one);//请求,1次
                }
            }
        }
    }

    public static class Reduce extends

            Reducer<Text, IntWritable, Text, IntWritable> {

        //实现reduce函数
        public void reduce(Text key, Iterable<IntWritable> values, Context context)

                throws IOException, InterruptedException {
            int total = 0;
            for (IntWritable val : values) {
                total += val.get();
            }
//            LOG.info("reduce request:" + key + ",count:" + total);
            context.write(key, new IntWritable(total));//请求，总次数
        }
    }


    public static void main(String[] args) throws Exception {

        LOG.info("program start......");

        Configuration conf = new Configuration();
        //这句话很关键
        conf.set("fs.default.name", "hdfs://192.168.56.99:9000");
//        conf.set("mapred.job.tracker", "192.168.56.99:9001");
        String[] ioArgs = new String[]{"/log", "/output/log_ip2"};
        String[] otherArgs = new GenericOptionsParser(conf, ioArgs).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: Data Sort <in> <out>");
            System.exit(2);
        }
        Job job = new Job(conf, "Data Sort");
        job.setJarByClass(KPIPV.class);
        //设置Map和Reduce处理类
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        //设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入和输出目录
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}
