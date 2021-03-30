package org.abf.hobt.servlet;

import org.abf.hobt.ApplicationInitializer;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dao.hibernate.HibernateConfiguration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.nio.file.Path;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class HobtServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        Path path = ApplicationInitializer.configure();
        if (path == null)
            return;

        try {
            HibernateConfiguration.beginTransaction();
            ApplicationInitializer.start(path);
            HibernateConfiguration.commitTransaction();
        } catch (Throwable e) {
            Logger.logErrorOnly(e);
            HibernateConfiguration.rollbackTransaction();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        Logger.info("Destroying Servlet Context");

        // shutdown executor service
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                Logger.info("De-registering JDBC driver: " + driver);
            } catch (SQLException e) {
                Logger.error("Error de-registering driver: " + driver, e);
            }
        }
    }
}
