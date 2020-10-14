package module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection conn = null;

    /**
     * Connect database
     *
     * @return <b> true </b> if connect successful else return <b>false</b> if connect error.
     */

    public static Connection getConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(
                    "jdbc:sqlserver://HUST\\HUST:2624;databaseName=PROJECT1",
                    "sa", "giatuyen");
        } catch (ClassNotFoundException | SQLException ex) {
        }
        return conn;
    }

}
