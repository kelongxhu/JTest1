package com.util.http.portal;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author codethink
 * @date 5/11/16 1:51 PM
 */
public class ReqHeader {
    private String status;
    private String location;
    private String xcache;


    @JSONField(serialize = false)
    private String header; // 可被过滤

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getXcache() {
        return xcache;
    }

    public void setXcache(String xcache) {
        this.xcache = xcache;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
