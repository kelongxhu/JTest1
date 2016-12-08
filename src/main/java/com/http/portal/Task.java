package com.http.portal;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author codethink
 * @date 5/11/16 2:16 PM
 */
public class Task {

    static Logger logger = Logger.getLogger("access");
    static Logger loggerError = Logger.getLogger("error");

    public static void main(String[] args) {
        ScheduledExecutorService schedu = Executors.newScheduledThreadPool(1);
        Runnable command = new Runnable() {
            @Override
            public void run() {
                //                System.out.println("开始请求");
                task();
            }
        };
        ScheduledFuture future = schedu.scheduleAtFixedRate(command, 2, 10, TimeUnit.SECONDS);
    }

    public static void task() {
        try {
            //判断www.alcatel-mobile.com是否正常
            //正常值:301,Miss from cloudfront,http://www.alcatel-mobile.com/global-en/
            String normalStatus = "301";
            String normalLocation = "www.alcatel-mobile.com";
            String xcache = "Miss from cloudfront";
            Command command = new Command();
            ReqHeader header = command.execue(Command.CMD);
            if (normalStatus.equals(header.getStatus()) && xcache.equals(header.getXcache())
                && header.getLocation().contains(normalLocation)) {
                //访问正常
                logger.info("请求/正常:" + JSON.toJSONString(header));
            } else {
                //访问异常
                ReqHeader header1 = command.execue(Command.CMD1);
                loggerError.info("请求/异常:" + JSON.toJSONString(header) + "=========>www1.X:"
                    + JSON.toJSONString(header1));
                loggerError.error("www.X/:" + header.getHeader());
                loggerError.error("www1.X/:" + header1.getHeader());
            }
            //判断www.alcatel-mobile.com/hk是否正常
            String hkStatus = "200";
            ReqHeader hkHeader = command.execue(Command.CMD2);
            if (hkStatus.equals(hkHeader.getStatus())) {
                //访问正常
                logger.info("请求/hk/正常:" + JSON.toJSONString(hkHeader));
            } else {
                //访问异常
                ReqHeader header2 = command.execue(Command.CMD3);
                loggerError.info("请求/异常:" + JSON.toJSONString(hkHeader) + "=========>www1.X/hk/:"
                    + JSON.toJSONString(header2));
                loggerError.error("www.X/hk/:" + hkHeader.getHeader());
                loggerError.error("www1.X/hk/:" + header2.getHeader());
            }
        } catch (Exception e) {
            loggerError.error(e.getMessage(), e);
        }
    }
}
