package com.hadoop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kelong on 7/30/14.
 */
public class DbCon {
    public static Connection getCon() {
        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://172.26.35.130:3306/hive_metadata";
        String user = "hive";
        String password = "hive";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("==================="+conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args){
        DbCon.getCon();
    }
}
