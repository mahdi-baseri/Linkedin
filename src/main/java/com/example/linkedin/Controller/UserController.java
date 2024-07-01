package com.example.linkedin.Controller;

import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


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
  public String getUsers() throws SQLException {
    ArrayList<User> users = userDatabase.getUsers();
    Gson gson = new Gson();
    String response = gson.toJson(users);
    return response;
  }


  public String getUserById(String id) throws SQLException {
    User user = userDatabase.getUser(id);
    if (user == null) return "No User";
    Gson gson = new Gson();
    String response = gson.toJson(user);
    return response;
  }

  public String getUserByEmail(String email) throws SQLException, JsonProcessingException {
    User user = userDatabase.getUser(email);
    if (user == null) {
      return null;
    } else {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(user);
    }
  }

  public String getUserByEmailAndPassword(String email, String password) throws SQLException, JsonProcessingException {
    User user = userDatabase.getUser(email, password);
    if (user == null) {
      return null;
    } else {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(user);
    }
  }


}
