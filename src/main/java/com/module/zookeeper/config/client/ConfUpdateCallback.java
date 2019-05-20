package com.module.zookeeper.config.client;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ke.long
 * @since 2019/5/5 14:13
 */
@Slf4j
public class ConfUpdateCallback implements ConfUpdate {
    private WatchMgr watchMgr;

    public ConfUpdateCallback(WatchMgr watchMgr){
        this.watchMgr=watchMgr;
    }
    @Override
    public void reload(String keyName) {
        log.info("refresh key config,{}",keyName);
        try {
            watchMgr.watchPath(keyName,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
