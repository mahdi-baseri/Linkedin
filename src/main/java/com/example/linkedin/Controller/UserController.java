package com.example.linkedin.Controller;

import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import java.sql.Date;
import java.sql.SQLException;



public class UserController {

  private static UserDatabase userDatabase;

  public UserController() throws SQLException {
    userDatabase = new UserDatabase();
  }

  public static void createUser(String id, String name, String lastName, String email,
      String phoneNumber, String password) throws SQLException {
    User user = new User(id, name, lastName, email, phoneNumber, password);
    userDatabase.addUser(user);
  }

  public void deleteUser(String id) throws SQLException {
    User user = new User();
    user.setId(id);
    userDatabase.deleteUser(user);
  }

  public void updateUser(String id, String firstName, String lastName, String email,
      String phoneNumber, String password) throws SQLException {
    User user = new User(id, firstName, lastName, email, phoneNumber, password);
    userDatabase.updateUser(user);
  }

}
