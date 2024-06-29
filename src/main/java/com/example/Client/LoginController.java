package com.example.Client;

import com.example.linkedin.DataAccess.DatabaseConnector;
import com.example.linkedin.DataAccess.UserDatabase;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

  @FXML
  private TextField email;
  @FXML
  private PasswordField passField;
  @FXML
  private Label warnning;
  @FXML
  private String emailOfUser;
  private String password;
  private Connection connection;
  private UserDatabase userDatabase;
  @FXML
  private Button loginBtn;
  @FXML
  private Button registerBtn;
  //private ProgressIndicator progressIndicator;


  public LoginController() throws SQLException {
    userDatabase = new UserDatabase();
    connection = DatabaseConnector.getConnection();
  }

  @FXML
  private void submit(ActionEvent event) throws SQLException, IOException {
    emailOfUser = email.getText();
    password = passField.getText();
    if (!userDatabase.emailExists(emailOfUser)) {
      email.setText("Email not exists");
      //warnning.setTextFill(Color.RED);
    } else {
      if (userDatabase.getUserByEmail(emailOfUser).getPassword() == password) {
        warnning.setText("Welcome Back " + emailOfUser);
        warnning.setTextFill(Color.GREEN);
        Parent loader = FXMLLoader.load(getClass().getResource("profile-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader));
        stage.show();
      }

    }
  }

  @FXML
  private void register(ActionEvent event) throws IOException {
    Parent loader = FXMLLoader.load(getClass().getResource("register-view.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(loader));
    stage.show();
  }
}