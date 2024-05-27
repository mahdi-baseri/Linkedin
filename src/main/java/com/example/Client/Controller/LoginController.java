package com.example.Client.Controller;

import com.example.Client.LoginPage;
import com.example.linkedin.DataAccess.DatabaseConnector;
import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

  @FXML
  private Label passwordLabel;
  @FXML
  private Label usernameLabel;
  @FXML
  private TextField userField;
  @FXML
  private PasswordField passField;
  @FXML
  private Label warnning;
  private String username;
  private String password;
  private Connection connection;
  private UserDatabase userDatabase;
  Stage stage;
  Parent root;
  @FXML
  private Button loginBtn;

  public LoginController() throws SQLException {
    userDatabase = new UserDatabase();
    connection = DatabaseConnector.getConnection();

  }

  @FXML
  protected void submit(ActionEvent event) throws SQLException, IOException {
    username = userField.getText();
    password = passField.getText();
    for (User user : userDatabase.getUsers()) {
      if (!userDatabase.userExists(username)) {
        warnning.setText("User not exists");
        warnning.setTextFill(Color.RED);
//        System.out.println("username : " + username + "\n" + "password :" + password);
      } else if (!user.getPassword().equals(password)) {
        warnning.setText("Wrong password");
        warnning.setTextFill(Color.RED);
//        System.out.println("Not Exists"); ّ
      } else {
        warnning.setText("Welcome Back " + username);
        warnning.setTextFill(Color.GREEN);
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setHeaderText("Welcome Back " + username);
//        alert.show();

      }
    }
  }
}