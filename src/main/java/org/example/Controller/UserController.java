package org.example.Controller;

import java.sql.Date;
import java.sql.SQLException;
import org.example.DataAccess.UserDatabase;
import org.example.Model.User;


public class UserController {

  private static UserDatabase userDatabase;

  public UserController() throws SQLException {
    userDatabase = new UserDatabase();
  }

  public static void createUser(String id, String name, String lastName, String email, String phoneNumber, String password, String country, Date birthday) throws SQLException {
    User user = new User(id, name, lastName, email, phoneNumber, password, country, birthday);
    userDatabase.addUser(user);
  }
}
