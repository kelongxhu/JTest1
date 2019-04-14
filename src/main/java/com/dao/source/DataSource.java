package com.dao.source;

/**
 * @author kelong
 * @since 2017/4/1 16:26
 */
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default DataSource.master;

    public static String master = "dataSource1";

    public static String slave1 = "dataSource2";

    public static String slave2 = "dataSource3";

}
