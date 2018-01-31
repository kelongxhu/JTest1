package com.job.elastic;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author codethink
 * @date 1/10/17 4:33 PM
 */
public class MyElasticJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(ShardingContext shardingContext) {

        logger.info("Thread ID: {}, 任务总片数: {}, 当前分片项: {},当前参数:{}", Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(), shardingContext.getShardingItem(), shardingContext.getShardingParameter());
        switch (shardingContext.getShardingItem()) {
            case 0:
                logger.info("执行0===>{}", shardingContext.getShardingParameter());
                break;
            case 1:
                logger.info("执行1===>{}", shardingContext.getShardingParameter());
                break;
            case 2:
                logger.info("执行2===>{}", shardingContext.getShardingParameter());
                break;
            default:
                break;
        }
    }
}
