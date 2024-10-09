package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    static final String DB_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "1243";

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
