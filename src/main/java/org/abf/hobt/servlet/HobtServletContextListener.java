package org.abf.hobt.servlet;

import org.abf.hobt.lib.common.Log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class HobtServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        init();
    }

    public void contextDestroyed(ServletContextEvent event) {
        Log.info("Destroying Servlet Context");

        // shutdown executor service
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                Log.info("De-registering JDBC driver: " + driver);
            } catch (SQLException e) {
                Log.error("Error de-registering driver: " + driver, e);
            }
        }
    }

    protected void init() {
    }
}
