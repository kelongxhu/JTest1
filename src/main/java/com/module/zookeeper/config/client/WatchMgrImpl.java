package com.module.zookeeper.config.client;

import com.google.common.base.Joiner;
import com.module.zookeeper.config.core.ZooUtils;
import com.module.zookeeper.config.core.ZookeeperMgr;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Watch 模块的一个实现
 *
 * @author liaoqiqi
 * @version 2014-6-10
 */
public class WatchMgrImpl implements WatchMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WatchMgrImpl.class);

    /**
     * zoo prefix
     */
    private String zooUrlPrefix;

    /**
     *
     */
    private boolean debug;

    /**
     * @Description: 获取自己的主备类型
     */
    @Override
    public void init(String hosts, String zooUrlPrefix, boolean debug) throws Exception {

        this.zooUrlPrefix = zooUrlPrefix;
        this.debug = debug;

        // init zookeeper
        ZookeeperMgr.getInstance().init(hosts, zooUrlPrefix, debug);
    }

    /**
     * 新建监控
     *
     * @throws Exception
     */
    private String makeMonitorPath(String key)  {
        String clientRootZooPath = ZooPathMgr.getZooBaseUrl(zooUrlPrefix, "app", "env", "version");
        ZookeeperMgr.getInstance().makeDir(clientRootZooPath, ZooUtils.getIp());
        // 新建Zoo Store目录
        String monitorPath = ZooPathMgr.joinPath(clientRootZooPath, key);
        // 先新建路径
        makePath(monitorPath, "");
        // 新建一个代表自己的临时结点
        makeTempChildPath(monitorPath, String.valueOf(System.currentTimeMillis()));

        return monitorPath;
    }

    /**
     * 创建路径
     */
    private void makePath(String path, String data) {
        ZookeeperMgr.getInstance().makeDir(path, data);
    }

    /**
     * 在指定路径下创建一个临时结点
     */
    private void makeTempChildPath(String path, String data) {

        String finerPrint = Joiner.on("-").join(ZooUtils.getIp(), UUID.randomUUID().toString());
        String mainTypeFullStr = path + "/" + finerPrint;
        try {
            ZookeeperMgr.getInstance().createEphemeralNode(mainTypeFullStr, data, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            LOGGER.error("cannot create: " + mainTypeFullStr + "\t" + e.toString());
        }
    }

    /**
     * 监控路径,监控前会事先创建路径,并且会新建一个自己的Temp子结点
     */
    @Override
    public void watchPath(String keyName,ConfUpdate confUpdate) throws Exception {
        // 新建
        String monitorPath = makeMonitorPath(keyName);
        // 进行监控
        NodeWatcher nodeWatcher = new NodeWatcher(monitorPath, keyName, confUpdate, debug);
        nodeWatcher.monitorMaster();
    }

    @Override
    public void release() {

        try {
            ZookeeperMgr.getInstance().release();
        } catch (Exception e) {

            LOGGER.error(e.toString());
        }
    }

}
