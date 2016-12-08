package com.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author kelong
 * @date 12/9/14
 */
@Component
public class AmoebaDao {
    Logger LOG=Logger.getLogger(AmoebaDao.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void amoebaTest(){
        String sql="select id,admin_name,admin_pass from user order by id";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        for(Map<String,Object> obj:list){
            LOG.info("========"+obj.get("admin_name"));
        }
    }

    public void amoebaInsertTest(){
        String sql="insert into user(admin_name,admin_pass)values(?,?)";
        jdbcTemplate.update(sql,new String[]{"a","a"});
    }
}
