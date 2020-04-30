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
    private static Connection connTest = null;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    private static String driverDbf = null;
    private static String driverPostgres = null;
    private static String urlDbf = null;
    private static String urlPostgres = null;
    private static String urlTest = null;
    private static String user = null;
    private static String userTest = null;
    private static String password = null;
    private static String passwordTest = null;


    static {
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

            urlTest = properties.getProperty("db.urlTest");
            userTest = properties.getProperty("db.usernameTest");
            passwordTest = properties.getProperty("db.passwordTest");
            log.debug("Loaded properties as Stream for Test: db.urlTest = {}, db.usernameTest = {})", urlTest, userTest);
        } catch (IOException e) {
            log.warn("Exception during Loaded properties from file {}.", new File("/persistence.properties").getPath(), e);
        }
    }

    public static Connection getConnDbf() {
        try {
            Class.forName(driverDbf);
            connDbf = DriverManager.getConnection(urlDbf);
            log.debug("Created connection for 'dbf-files' from 1C. Url= {}.", urlDbf);
        } catch (SQLException | ClassNotFoundException e) {
            log.warn("Exception during create connection for 'dbf-files' from 1C. Url= {}.", urlDbf, e);
        }
        return connDbf;
    }

    public static Connection getConnPostgres() {
        try {
            Class.forName(driverPostgres);
            connPostgres = DriverManager.getConnection(urlPostgres, user, password);
            connPostgres.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connPostgres.setAutoCommit(false);
            log.debug("Created connection for 'postgres'. Url= {}, user= {}.", urlPostgres, user);
        } catch (SQLException | ClassNotFoundException e) {
            log.warn("Exception during create connection for 'postgres' url= {}, user= {}.", urlPostgres, user, e);
        }
        return connPostgres;
    }

    public static Connection getConnTest() {
        try {
            Class.forName(driverPostgres);
            connTest = DriverManager.getConnection(urlTest, userTest, passwordTest);
            connTest.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connTest.setAutoCommit(false);
            log.debug("Created connection for 'postgres'. Url= {}, user= {}.", urlTest, userTest);
        } catch (SQLException | ClassNotFoundException e) {
            log.warn("Exception during create connection for 'postgres' url= {}, user= {}.", urlTest, userTest, e);
        }
        return connTest;
    }
}
