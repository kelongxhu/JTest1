package com.module.job.elastic;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author ke.long
 * @since 2018/10/16 11:35
 */
public class JobDemo {
    public static void main(String[] args) {
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.56.101:2181", "elastic-job-demo"));
        regCenter.init();
        return regCenter;
    }

    private static LiteJobConfiguration createJobConfiguration() {
        // 创建作业配置
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 5).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MyElasticJob.class.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
        // ...
        return simpleJobRootConfig;
    }
}
