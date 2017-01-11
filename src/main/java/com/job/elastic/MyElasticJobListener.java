package com.job.elastic;

/**
 * @author codethink
 * @date 1/10/17 5:47 PM
 */
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.api.listener.ElasticJobListener;

public class MyElasticJobListener implements ElasticJobListener {

    public void beforeJobExecuted(final JobExecutionMultipleShardingContext shardingContext) {
        // do something ...
        System.out.println("start.............."+shardingContext.getShardingItems());
    }

    public void afterJobExecuted(final JobExecutionMultipleShardingContext shardingContext) {
        // do something ...
        System.out.println("end..............");

    }
}
