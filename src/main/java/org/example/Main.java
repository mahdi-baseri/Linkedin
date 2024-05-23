package org.example;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.Controller.UserController;
import org.example.DataAccess.DatabaseConnector;
import org.example.DataAccess.UserDatabase;
import org.example.Model.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  public static void main(String[] args) throws SQLException {
    User user = new User("4165", "elham", "asghari", "sanafas6961@gmail.com", "09371962511",
        "elham468545", "Iran", new Date(2024, 7, 16));
    UserDatabase userDatabase = new UserDatabase();
    userDatabase.addUser(user);
  }
}
