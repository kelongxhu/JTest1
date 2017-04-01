package com.dao.mybatis;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TableSeg {
    /**
     * 表名
     * @return
     */
    public String tableName();
    /**
     * 分表方式，取模，如%5：表示取5余数，
     * 如果不设置，直接根据shardBy值分表
     * @return
     */
    public String shardType();
    /**
     * 根据什么字段分表
     * 多个字段用数学表达表示,如a+b   a-b
     * @return
     */
    public String shardBy();

}
