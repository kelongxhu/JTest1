package com.module.zookeeper.config.client;

import com.module.zookeeper.config.core.ZookeeperMgr;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 结点监控器
 *
 * @author liaoqiqi
 * @version 2014-6-16
 */
public class NodeWatcher implements Watcher {

    protected static final Logger LOGGER = LoggerFactory.getLogger(NodeWatcher.class);

    private String monitorPath = "";
    private String keyName = "";
    private ConfUpdate confUpdateCallback;
    private boolean debug;
    /**
     */
    public NodeWatcher(String monitorPath, String keyName,
                       ConfUpdate confUpdateCallback,
                       boolean debug) {

        super();
        this.debug = debug;
        this.monitorPath = monitorPath;
        this.keyName = keyName;
        this.confUpdateCallback = confUpdateCallback;
    }

    /**
     */
    public void monitorMaster() {
        Stat stat = new Stat();
        try {
            ZookeeperMgr.getInstance().read(monitorPath, this, stat);
        } catch (Exception e) {
            LOGGER.info(e.toString());
        }
        LOGGER.info("monitor path: (" + monitorPath + "," + keyName + ","  + ") has been added!");
    }

    /**
     * 回调函数
     */
    @Override
    public void process(WatchedEvent event) {

        //
        // 结点更新时
        //
        if (event.getType() == EventType.NodeDataChanged) {

            try {
                LOGGER.info("============GOT UPDATE EVENT " + event.toString() + ": (" + monitorPath + "," + keyName
                        +  ")======================");
                // 调用回调函数, 回调函数里会重新进行监控
                callback();
            } catch (Exception e) {
                LOGGER.error("monitor node exception. " + monitorPath, e);
            }
        }

        //
        // 结点断开连接，这时不要进行处理
        //
        if (event.getState() == KeeperState.Disconnected) {

            if (!debug) {
                LOGGER.warn("============GOT Disconnected EVENT " + event.toString() + ": (" + monitorPath + ","
                        + keyName  + ")======================");
            } else {
                LOGGER.debug("============DEBUG MODE: GOT Disconnected EVENT " + event.toString() + ": (" +
                        monitorPath +
                        "," +
                        keyName + ")======================");
            }
        }

        //
        // session expired，需要重新关注哦
        //
        if (event.getState() == KeeperState.Expired) {

            if (!debug) {

                LOGGER.error("============GOT Expired  " + event.toString() + ": (" + monitorPath + "," + keyName
                        + ")======================");

                // 重新连接
                ZookeeperMgr.getInstance().reconnect();

                callback();
            } else {
                LOGGER.debug("============DEBUG MODE: GOT Expired  " + event.toString() + ": (" + monitorPath + ","
                        + "" + keyName + ")======================");
            }
        }
    }

    /**
     *
     */
    private void callback() {
            // 调用回调函数, 回调函数里会重新进行监控
            try {
                confUpdateCallback.reload(keyName);
            } catch (Exception e) {
                LOGGER.error(e.toString(), e);
            }
    }
}
