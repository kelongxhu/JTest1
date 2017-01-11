package com.job.elastic;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualThroughputDataFlowElasticJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author codethink
 * @date 1/10/17 5:33 PM
 */
public class ThroughputDataFlowElasticJob extends AbstractIndividualThroughputDataFlowElasticJob<Foo> {
    public List<Foo> fetchData(JobExecutionMultipleShardingContext context) {
        //根据分片筛选
        Map<Integer, String> offset = context.getOffsets();
        List<Foo> result = new ArrayList<Foo>();
        result.add(new Foo(1));
        result.add(new Foo(2));
        return result;
    }

    public boolean processData(JobExecutionMultipleShardingContext context, Foo data) {
        // process data
        System.out.println("processData:"+data.getId());
        // store offset
        for (int each : context.getShardingItems()) {
            updateOffset(each, "your offset, maybe id");
        }
        return true;
    }
}
