package com.example.linkedin.Controller;

import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;;
import java.sql.SQLException;



public class UserController {

  private static UserDatabase userDatabase;

  public UserController() throws SQLException {
    userDatabase = new UserDatabase();
  }

  public static void createUser(String id ,String name, String lastName, String country, String email,
                                String phoneNumber, String password) throws SQLException {
    User user = new User(id , name, lastName, country, email, phoneNumber, password);
    userDatabase.addUser(user);
  }


  public User getUser(String email , String password) throws SQLException {
    User user = userDatabase.getUser(email , password);
    if (user == null) {
      return null;
    } else {
      return user ;
    }
  }

}
