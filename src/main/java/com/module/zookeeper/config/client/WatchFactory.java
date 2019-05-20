package com.module.zookeeper.config.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ke.long
 * @since 2019/5/5 14:18
 */
public class WatchFactory {
    protected static final Logger LOGGER = LoggerFactory.getLogger(WatchFactory.class);

    /**
     * @throws Exception
     */
    public static WatchMgr getWatchMgr(String hosts, String zooPrefix) throws Exception {
        WatchMgr watchMgr = new WatchMgrImpl();
        watchMgr.init(hosts, zooPrefix, true);
        return watchMgr;
    }

    public static void main(String[] args) throws Exception {
        WatchMgr watchMgr = WatchFactory.getWatchMgr("192.168.56.101:2181", "/config");
        watchMgr.watchPath("route_config", new ConfUpdateCallback(watchMgr));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
