package com.module.hadoop.loganalyse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by kelong on 8/5/14.
 */
public class KPIIP {

    private static final Log LOG = LogFactory.getLog(KPIIP.class);
    //map将输入中的value复制到输出数据的key上，并直接输出

    public static class Map extends Mapper<Object, Text, Text, Text> {
        private static Text request = new Text();//每行数据

        //实现map函数
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            LOG.info("file input value:" + value);
            KPI kpi=KPI.filterIPs(value.toString());
            request.set(kpi.getRequest());
            context.write(request, new Text(kpi.getRemote_addr()));//请求，
        }
    }
}
