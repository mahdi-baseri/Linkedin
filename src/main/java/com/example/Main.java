package com.example;


import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import java.sql.Date;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  public static void main(String[] args) throws SQLException {
    User user = new User("45fdcfsxgsedfd4165", "mamad", "asghari", "sanafas6961@gmail.com", "09371962511","123");
    UserDatabase userDatabase = new UserDatabase();
    userDatabase.addUser(user);
  }
}
