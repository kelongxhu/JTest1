package com.service.impl;

import com.service.CacheProvider;
import com.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ke.long
 * @since 2019/4/8 14:03
 */
@Service
public class MockServiceImpl implements MockService {
    @Autowired
    private CacheProvider cacheProvider;

    @Override
    public String getValue() {
        return cacheProvider.getValue("1","a");
    }
}
