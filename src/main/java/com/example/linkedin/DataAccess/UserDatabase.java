package com.example.linkedin.DataAccess;

import com.example.linkedin.Model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDatabase {

  private final Connection connection = DatabaseConnector.getConnection();

  public UserDatabase() throws SQLException {
    createTable();
  }


  public void createTable() throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "CREATE TABLE IF NOT EXISTS users (id VARCHAR(255) NOT NULL , name VARCHAR(255) NOT NULL , lastName VARCHAR(255) NOT NULL , email VARCHAR(255) NOT NULL , phoneNumber VARCHAR(12) NOT NULL , password VARCHAR(255) NOT NULL , country VARCHAR(255) NOT NULL , birthday DATE NOT NULL , created_at DATE NOT NULL , PRIMARY KEY (id)) ENGINE = InnoDB");
    statement.executeUpdate();
  }

  // add a user to db
  public void addUser(User user) throws SQLException {
//    here we add our user data to the table user from the database
    if (emailExists(user.getEmail())==true) {
      System.out.println("user already exist . please change email");
    }
    PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?)");
    statement.setString(1, user.getId());
    statement.setString(2, user.getName());
    statement.setString(3, user.getLastName());
    statement.setString(4, user.getEmail());
    statement.setString(5, user.getPhoneNumber());
    statement.setString(6, user.getPassword());
    statement.setString(7, "Tehran");
//    here we cant cast Date to java.sql.date and because or that i add the java.sql.Date method
   statement.setDate(8, new Date(2024,5,2));
   statement.setDate(9, new Date(user.getCreated_at().getTime()));
    statement.executeUpdate();
  }

  // delete a user from users table
  public void deleteUser(User user) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
    statement.setString(1, user.getId());
    statement.executeUpdate();
  }

  //  update a user's information
  public void updateUser(User user) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "UPDATE users SET name = ?, lastname = ?, email = ?, phoneNumber = ?, password = ?, country = ?, birthday = ? WHERE id = ?");
    statement.setString(1, user.getName());
    statement.setString(2, user.getLastName());
    statement.setString(3, user.getEmail());
    statement.setString(4, user.getPhoneNumber());
    statement.setString(5, user.getPassword());
    statement.setString(6, user.getCountry());
    statement.setDate(7, new Date(user.getBirthday().getTime()));
    statement.setString(8, user.getId());
    statement.executeUpdate();
  }

  // getting user from database with id
  public User getUser(String id) throws SQLException {
    PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
    statement.setString(1, id);
    ResultSet result = statement.executeQuery();

    if (result.next()) {
      User user = new User();
      user.setId(result.getString("id"));
      user.setName(result.getString("name"));
      user.setLastName(result.getString("lastName"));
      user.setEmail(result.getString("email"));
      user.setPhoneNumber(result.getString("phoneNumber"));
      user.setPassword(result.getString("password"));
      user.setCountry(result.getString("country"));
      user.setBirthday(result.getDate("birthday"));
      return user;
    }

    return null;
  }

  // get all users
  public ArrayList<User> getUsers() throws SQLException {
    ArrayList<User> users = new ArrayList<>();
    PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
    ResultSet result = statement.executeQuery();
    while (result.next()) {
      User user = new User();
      user.setId(result.getString("id"));
      user.setName(result.getString("name"));
      user.setLastName(result.getString("lastName"));
      user.setEmail(result.getString("email"));
      user.setPhoneNumber(result.getString("phoneNumber"));
      user.setPassword(result.getString("password"));
      user.setCountry(result.getString("country"));
      user.setBirthday(result.getDate("birthday"));
      users.add(user);
    }

    return users;
  }

  public boolean userExists(String username) throws SQLException {
    for (User user : getUsers()) {
      if (user.getName().equals(username)) {
        return true;
      }
    }
    return false;
  }

  public boolean idExists(String id) throws SQLException {
    for (User user : getUsers()) {
      if (user.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

  public boolean emailExists(String email) throws SQLException {
    for (User user : getUsers()) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }
}
