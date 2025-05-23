package org.abf.hobt.servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.abf.hobt.ApplicationInitializer;
import org.abf.hobt.common.logging.Logger;
import org.abf.hobt.dao.hibernate.HibernateConfiguration;

import java.nio.file.Path;

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
        HibernateConfiguration.close();
    }
}
