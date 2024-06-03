package com.danyatheworst;

import com.danyatheworst.utils.HibernateUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class HibernateInitializer implements ServletContextListener {
    private Server webServer;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HibernateUtil.getSessionFactory();

        // Start H2 Web Console on port 9092
        try {
            webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "9092");
            webServer.start();
            System.out.println("H2 Web Console started at http://localhost:9092");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to start H2 web console", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (webServer != null) {
            webServer.stop();
        }
        HibernateUtil.getSessionFactory().close();
    }
}