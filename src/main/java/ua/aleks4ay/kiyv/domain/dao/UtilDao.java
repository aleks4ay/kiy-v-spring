package ua.aleks4ay.kiyv.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class UtilDao {
    private static Connection connDbf = null;
    private static Connection connPostgres = null;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    static {
        String driverDbf = null;
        String urlDbf = null;
        String driverPostgres = null;
        String urlPostgres = null;
        String user = null;
        String password = null;

        //load DB properties
        try (InputStream in = UtilDao.class.getClassLoader().getResourceAsStream("persistence.properties")){
            Properties properties = new Properties();
            properties.load(in);
            driverDbf = properties.getProperty("dbf.driverClassName");
            urlDbf = properties.getProperty("dbf.url");

            driverPostgres = properties.getProperty("database.driverClassName");
            urlPostgres = properties.getProperty("database.url");
            user = properties.getProperty("database.username");
            password = properties.getProperty("database.password");
            log.debug("Loaded properties as Stream: dbf.driverClassName = {}, dbf.url = {}, database.driverClassName = {}, " +
                            "database.url = {}, database.username = {})",
                    driverDbf, urlDbf, driverPostgres, urlPostgres, user);
        } catch (IOException e) {
            log.warn("Exception during Loaded properties from file {}.", new File("/persistence.properties").getPath(), e);
        }

        //acquire DB connection to DBF
        try {
            Class.forName(driverDbf);
            connDbf = DriverManager.getConnection(urlDbf);
            log.debug("Created connection for 'dbf-files' from 1C. Url= {}.", urlDbf);
        } catch (SQLException | ClassNotFoundException e) {
            log.warn("Exception during create connection for 'dbf-files' from 1C. Url= {}.", urlDbf, e);
        }

        //acquire DB connection to Postgres
        try {
            Class.forName(driverPostgres);
            connPostgres = DriverManager.getConnection(urlPostgres, user, password);
            log.debug("Created connection for 'postgres'. Url= {}, user= {}.", urlPostgres, user);
        } catch (SQLException | ClassNotFoundException e) {
            log.warn("Exception during create connection for 'postgres' url= {}, user= {}.", urlPostgres, user, e);
        }
    }

    public static Connection getConnDbf() {
//        log.debug("Try return connection for 'dbf-files' from 1C.");
        return connDbf;
    }

    public static Connection getConnPostgres() {
//        log.debug("Try return connection for Postgres.");
        return connPostgres;
    }

/*    @Override
    protected void finalize ( ) {
        try {
            log.debug("Try close DBF-connection.");
            connDbf.close();
            log.debug("Closed DBF-connection. OK.");
        } catch (SQLException e) {
            log.warn("Exception during close DBF-connection.", e);
        }
        try {
            log.debug("Try close connection for Postresql.");
            connPostgres.close();
            log.debug("Closed connection for Postresql. OK");
        } catch (SQLException e) {
            log.warn("Exception during close connection for Postresql.", e);
        }
    }*/
}
