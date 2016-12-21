import com.quzrtz.hello.ScheduleJobEntity;
import com.quzrtz.hello.ScheduleUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author codethink
 * @date 12/21/16 4:47 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class JobTest {
    @Resource
    private Scheduler scheduler;

    @Test
    public void addTest() throws Exception{
        for (int i = 0; i < 5; i++) {
            ScheduleJobEntity scheduleJob = new ScheduleJobEntity();

            scheduleJob.setJobId(new Long(i));
            scheduleJob.setCreateTime(new Date());
            scheduleJob.setBeanName("sayHello");
            scheduleJob.setMethodName("say");
            scheduleJob.setParams("job" + i);
            scheduleJob.setCronExpression("0/5 * * * * ?");
            scheduleJob.setStatus(0);

            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        }


        ScheduleUtils.getAllJob(scheduler);

        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
