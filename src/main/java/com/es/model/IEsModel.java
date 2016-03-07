package com.es.model;

/**
 * @author kelong
 * @date 10/28/15
 */
public interface IEsModel {
    public String getDocId();

    public String getEsIndexName();

    public String getEsTypeName();

    public String getJson()throws Exception;
}
