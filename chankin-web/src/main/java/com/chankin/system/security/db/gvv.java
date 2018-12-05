package com.chankin.system.security.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;

/*
 *  关闭数据库连接,防止内存溢出
 *
 * */
public class gvv extends DruidDataSource {
    @Override
    public void close() {
        super.close();
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
