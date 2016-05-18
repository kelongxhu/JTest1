package com.es.forum.service;

import com.es.forum.model.PostModel;

/**
 * @author kelong
 * @date 10/28/15
 */
public interface PostEsService {
    public void addPostDoc(PostModel postModel)throws Exception;
}
