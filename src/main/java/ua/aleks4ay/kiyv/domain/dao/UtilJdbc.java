package ua.aleks4ay.kiyv.domain.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilJdbc {

    private static boolean initialized;

    private UtilJdbc() {
    }

    public static synchronized void initDriver(String driverClassName) {
        if (! initialized) {
            try {
                Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't initialize driver '" + driverClassName + "'", e);
            }
            initialized = true;
        }
    }

    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                // NOP
            }
        }
    }


    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }
}
