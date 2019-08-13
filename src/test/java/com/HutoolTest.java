package com;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Map;

/**
 * @author ke.long
 * @since 2019/6/28 14:02
 */
public class HutoolTest {
    @Test
    public void testHutool(){
        Map<String, String> paramMap = HttpUtil.decodeParamMap("/auth/token?key=a&secret=b", CharsetUtil.UTF_8);
        System.out.println(JSON.toJSONString(paramMap));
    }
}
