package cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseClass {
    static Connection connection;

    static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb",
                "root", "");

    }

    static void terminateConnection() throws SQLException{
        connection.close();
    }
}
