//$Id$
package com.shopping.cart;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DataBaseClass {
	public static Connection connection;

    public static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb",
                "root", "");

    }

    public static void terminateConnection() throws SQLException{
        connection.close();
    }
}
