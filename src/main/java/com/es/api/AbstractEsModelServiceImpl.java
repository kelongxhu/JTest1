package com.es.api;

import com.es.model.IEsModel;
import com.es.util.EsClient;

/**
 * @author kelong
 * @date 10/28/15
 */
public abstract class AbstractEsModelServiceImpl implements IEsModelService {
    @Override
    public void addDocument(IEsModel model) throws Exception {
        if (null == model) {
            return;
        }

        Object docId = model.getDocId();
        if (null == docId || "".equals(docId)) {
            throw new RuntimeException("Validation Failed: DocId field is missing by model. "
                + model.getDocId());
        }

        EsClient.getInstance().addIndex(model);
    }
}
