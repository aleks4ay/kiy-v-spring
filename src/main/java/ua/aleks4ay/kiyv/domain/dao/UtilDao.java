package ua.aleks4ay.kiyv.domain.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UtilDao {
    private static Connection connDbf = null;
    private static Connection connPostgres = null;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        //acquire DB connection to DBF
        try {
            Class.forName(driverDbf);
            connDbf = DriverManager.getConnection(urlDbf);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //acquire DB connection to Postgres
        try {
            Class.forName(driverPostgres);
            connPostgres = DriverManager.getConnection(urlPostgres, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnDbf() {
        return connDbf;
    }

    public static Connection getConnPostgres() {
        return connPostgres;
    }
}
