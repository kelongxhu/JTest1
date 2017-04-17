package com.util.http.portal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author codethink
 * @date 5/11/16 1:13 PM
 */
public class Command {
    public static final String CMD = "curl -I www.baidu.com";
    public static final String CMD1 = "curl -I www1.baidu.com";
    public static final String CMD2 = "curl -I www.baidu.com/hk/";
    public static final String CMD3 = "curl -I www1.baidu.com/hk/";

    public ReqHeader execue(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(
            new String[] {"/bin/sh", "-c", cmd});
        InputStream is = process.getInputStream();
        BufferedReader br = new BufferedReader(
            new InputStreamReader(is, "utf8"));
        ReqHeader reqHeader = new ReqHeader();
        StringBuilder hearder = new StringBuilder();
        try {
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                if (tmp.startsWith("HTTP/1.1")) {
                    reqHeader.setStatus(tmp.substring(9, 12));
                } else if (tmp.startsWith("Location:")) {
                    reqHeader.setLocation(tmp.substring(tmp.indexOf(":") + 2));
                } else if (tmp.startsWith("X-Cache:")) {
                    reqHeader.setXcache(tmp.substring(tmp.indexOf(":") + 2));
                }
                hearder.append(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            process.destroy();
            is.close();
            br.close();
        }
        if (reqHeader.getStatus() == null) {
            return null;
        }
        reqHeader.setHeader(hearder.toString());
        return reqHeader;
    }

    public static void main(String[] args) throws Exception {
        Command command = new Command();
        command.execue(CMD);
    }
}



