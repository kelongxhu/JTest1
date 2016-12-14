package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


/**
 * @author codethink
 * @date 12/14/16 5:20 PM
 */
public class ConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    private static final String APP_CONF_NAME = "app_conf";
    private static final String serverConfigFile = "serverConfig.properties";



    public static Properties getConfigs(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            logger.error("filename is invalid. Please fix it.");
            return null;
        }
        String confPath = getAppConfPath();
        InputStream inputStream = null;
        if (confPath == null) {
            logger.warn("Not {} parameter be specified,try to load resource from classpath",
                APP_CONF_NAME);
            inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);
        } else {
            String filePath = confPath + fileName;
            try {
                inputStream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                logger.error("file not found:" + filePath);
                return null;
            }
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

        return properties;
    }


    public static Properties getPropertiesByFile(String fileName) {
        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName);

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.getMessage();
        }
        return properties;
    }


    public static String getAppConfPath() {
        String confPath = System.getProperty(APP_CONF_NAME);
        if (confPath == null) {
            return null;
        }
        if (confPath.endsWith("/")) {
            return confPath;
        }
        return confPath + "/";
    }


}
