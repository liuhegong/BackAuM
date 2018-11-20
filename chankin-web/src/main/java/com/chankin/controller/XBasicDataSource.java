package com.chankin.controller;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.jdbc.SQL;

import java.sql.DriverManager;
import java.sql.SQLException;

public class XBasicDataSource extends DruidDataSource {
    @Override
    public void close() {
        try {
            DriverManager.deregisterDriver(DriverManager.getDriver("jdbc:mysql://localhost:3306/chankin?useUnicode=true&characterEncoding=utf-8"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();
    }
}