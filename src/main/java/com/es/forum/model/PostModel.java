package com.es.forum.model;

import com.alibaba.fastjson.JSON;
import com.es.model.IEsModel;

/**
 * @author kelong
 * @date 10/28/15
 */
public class PostModel implements IEsModel {

    private PostMeta postMeta;

    public PostModel(PostMeta postMeta) {
        this.postMeta = postMeta;
    }

    @Override
    public String getDocId() {
        return postMeta.getId();
    }

    @Override
    public String getEsIndexName() {
        return "forum";
    }

    @Override
    public String getEsTypeName() {
        return "post";
    }

    @Override
    public String getJson() throws Exception {
        return JSON.toJSONString(postMeta);
    }
}
