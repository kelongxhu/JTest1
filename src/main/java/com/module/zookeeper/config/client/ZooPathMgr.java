package com.module.zookeeper.config.client;

/**
 * Zoo path 管理
 *
 * @author liaoqiqi
 * @version 2014-6-16
 */
public class ZooPathMgr {

    /**
     * @return String
     *
     * @Description: 获取ZOOKEEPER的应用基础路径
     * @author liaoqiqi
     * @date 2013-6-17
     */
    public static String getZooBaseUrl(String urlPrefix, String app, String env, String version) {

        StringBuffer sb = new StringBuffer();
        sb.append(urlPrefix);

        sb.append("/");
        sb.append(app);

        sb.append("_");
        sb.append(version);

        sb.append("_");
        sb.append(env);

        return sb.toString();
    }

    /**
     * @param path1
     * @param path2
     *
     * @return
     */
    public static String joinPath(String path1, String path2) {
        return path1 + "/" + path2;
    }
}
