package org.example.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

  private static Connection connection;
  private static final String url = "jdbc:mysql://localhost:3306/linkedin";
  private static final String username = "root";
  private static final String password = "";

  public static Connection getConnection() {
    try {
      if (connection == null) {
        connection = DriverManager.getConnection(url, username, password);
      } else if (connection.isClosed()) {
        connection = DriverManager.getConnection(url, username, password);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      e.printStackTrace();
    }
    return connection;
  }

  private DatabaseConnector() throws SQLException {
  }
}
