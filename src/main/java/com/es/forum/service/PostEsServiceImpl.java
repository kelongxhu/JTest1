package com.es.forum.service;

import com.es.api.AbstractEsModelServiceImpl;
import com.es.forum.model.PostModel;
import org.springframework.stereotype.Service;

/**
 * @author kelong
 * @date 10/28/15
 */
@Service
public class PostEsServiceImpl extends AbstractEsModelServiceImpl implements PostEsService {
    @Override
    public void addPostDoc(PostModel postModel)throws Exception{
        addDocument(postModel);
    }
}
