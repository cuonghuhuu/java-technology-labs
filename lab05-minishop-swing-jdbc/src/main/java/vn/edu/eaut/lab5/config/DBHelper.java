package vn.edu.eaut.lab5.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBHelper {

    private static final String URL =
            "jdbc:mysql://localhost:3306/minishop_db"
            + "?useUnicode=true"
            + "&characterEncoding=UTF-8";

    private static final String USER = "root";

    private static final String PASSWORD =
            System.getenv().getOrDefault(
                    "MINISHOP_DB_PASSWORD",
                    ""
            );

    private DBHelper() {
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    public static boolean testConnection() {

        try (Connection conn = getConnection()) {

            return conn != null && !conn.isClosed();

        } catch (SQLException ex) {

            System.err.println(
                    "Ket noi CSDL that bai: "
                    + ex.getMessage()
            );

            return false;
        }
    }
}