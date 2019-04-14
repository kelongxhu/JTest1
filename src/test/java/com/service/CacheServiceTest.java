package com.service;

import com.BaseMockTest;
import com.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;

/**
 * @author kelong
 * @since 2017/6/30 15:03
 */
@Slf4j
public class CacheServiceTest extends BaseMockTest {

    @InjectMocks
    @Resource
    private MockService mockService;

    @Mock
    private CacheProvider cacheProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getValueTest() {

        when(cacheProvider.getValue("1","a")).
                thenReturn("aaa");

        log.info("mock return:{}",cacheProvider.getValue("1","a"));

        log.info("return {}",mockService.getValue());
    }
}
