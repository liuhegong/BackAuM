package com.chankin.system.security.db;

import com.mysql.jdbc.ConnectionImpl;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;

public class gvvv extends ConnectionImpl {
    @Override
    public void close() throws SQLException {
        super.close();
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
