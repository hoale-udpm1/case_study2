package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LienKet {

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
                        "jdbc:sqlserver:localhost:3306/thuvien",
                        "admin", "admin");
            } catch (ClassNotFoundException | SQLException ex) {
            }
            return conn;
        }

    }

