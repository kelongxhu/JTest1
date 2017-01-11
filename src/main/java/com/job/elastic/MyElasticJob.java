package com.job.elastic;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;

/**
 * @author codethink
 * @date 1/10/17 4:33 PM
 */
public class MyElasticJob extends AbstractSimpleElasticJob {

    public void process(JobExecutionMultipleShardingContext context) {
        // do something by sharding items

        System.out.println("JOB:"+JSON.toJSONString(context.getShardingItems()));
    }
}
