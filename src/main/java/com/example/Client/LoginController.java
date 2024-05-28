package com.example.Client;

import com.example.linkedin.DataAccess.DatabaseConnector;
import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
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
  @FXML
  private Button loginBtn;
  private ProgressIndicator progressIndicator;

  public LoginController() throws SQLException {
    userDatabase = new UserDatabase();
    connection = DatabaseConnector.getConnection();

  }

  @FXML
  private void submit(ActionEvent event) throws SQLException, IOException {
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
        Parent loader = FXMLLoader.load(getClass().getResource("profile-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(fxmlLoader, 600, 400);
        stage.setScene(new Scene(loader));
        stage.show();
      }
    }
  }

  @FXML
  private void register(ActionEvent event) throws IOException {
    Parent loader = FXMLLoader.load(getClass().getResource("register-view.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(fxmlLoader, 600, 400);
    stage.setScene(new Scene(loader));
    stage.show();
  }
}