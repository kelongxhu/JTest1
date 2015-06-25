package com.quzrtz;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @author kelong
 * @date 5/20/15
 */
public class JobTest {
    public static void main(String[] args)throws Exception{


        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        Job job = (Job) factory.getBean("job");


        for(int i=0;i<5;i++){
            ScheduleJob scheduleJob=new ScheduleJob();

            scheduleJob.setJobId(new Long(i));
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setUpdateTime(new Date());
            scheduleJob.setBeanClass("com.quzrtz.SayHello");
            scheduleJob.setMethodName("say");
            scheduleJob.setJobGroup("jobGroup");
            scheduleJob.setJobName("job"+i);
            scheduleJob.setJobStatus("1");
            scheduleJob.setCronExpression("0/5 * * * * ?");

            job.addJob(scheduleJob);
        }


    }
}
