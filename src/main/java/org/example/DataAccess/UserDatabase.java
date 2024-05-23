package org.example.DataAccess;

import org.example.Model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase {

  private final Connection connection = DatabaseConnector.getConnection();

  public UserDatabase() throws SQLException {
    createTable();
  }

  public void createTable() throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "CREATE TABLE IF NOT EXISTS users (id VARCHAR(255) NOT NULL , name VARCHAR(255) NOT NULL , lastName VARCHAR(255) NOT NULL , email VARCHAR(255) NOT NULL , phonNumber VARCHAR(12) NOT NULL , password VARCHAR(255) NOT NULL , country VARCHAR(255) NOT NULL , birthday DATE NOT NULL , created_at DATE NOT NULL , PRIMARY KEY (id)) ENGINE = InnoDB;");
    statement.executeUpdate();
  }

  public void addUser(User user) throws SQLException {
//    here we add our user data to the table user from the database
    PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?)");
    statement.setString(1, user.getId());
    statement.setString(2, user.getName());
    statement.setString(3, user.getLastName());
    statement.setString(4, user.getEmail());
    statement.setString(5, user.getPhoneNumber());
    statement.setString(6, user.getPassword());
    statement.setString(7, user.getCountry());
//    here we cant cast Date to java.sql.date and because or that i add the java.sql.Date method
    statement.setDate(8, new Date(user.getBirthday().getTime()));
    statement.setDate(9, new Date(user.getCreated_at().getTime()));
    statement.executeUpdate();
  }
}
