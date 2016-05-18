package com.es.api;

import com.es.model.IEsModel;

/**
 * @author kelong
 * @date 10/28/15
 */
public interface IEsModelService {
    /**
     * @param model
     * @throws Exception
     */
    public abstract void addDocument(IEsModel model) throws Exception;
}
