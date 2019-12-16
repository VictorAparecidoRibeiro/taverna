package br.com.projeto.agenda.util.app;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("deregistering jdbc driver: " + driver);
                
            } catch (SQLException e) {
                System.out.println("Error deregistering driver" + driver + " erro" + e);
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //Indica para JSF não conveter int e long para 0 automaticamente
        System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
    }

}
