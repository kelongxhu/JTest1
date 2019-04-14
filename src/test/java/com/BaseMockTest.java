package com;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.AopTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author ke.long
 * @since 2019/4/8 13:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
        {
                "classpath*:applicationContext.xml"
        })
@WebAppConfiguration
//@ActiveProfiles("unittest")
@Transactional
@Slf4j
public abstract class BaseMockTest extends AbstractTransactionalJUnit4SpringContextTests {
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(context)
                .build();
    }

    public <T> T unwrapProxy(Object bean) throws Exception {
        return AopTestUtils.getUltimateTargetObject(bean);
    }

}
