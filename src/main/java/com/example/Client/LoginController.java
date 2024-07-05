package com.example.Client;

import com.example.linkedin.DataAccess.DatabaseConnector;
import com.example.linkedin.DataAccess.UserDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
import javafx.scene.control.TextField;
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


  @FXML
  private void submit(ActionEvent event) throws IOException {
    emailOfUser = email.getText();
    password = passField.getText();


      if ((email.getText().equals("")) || (passField.getText().equals(""))) {
        warnning.setTextFill(Color.RED);
        warnning.setText("Please compelte fields");

      } else if (!email.getText().endsWith("@gmail.com")) {
        warnning.setTextFill(Color.RED);
        warnning.setText("please check your email");

      } else {

        URL url = new URL("http://localhost:8000/" + "login/" + emailOfUser + "/" + password);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == 409) {
          warnning.setText("User not found.Incorrect password or email");
        } else if (responseCode == 400) {
          warnning.setText("Invalid path");
        } else {
          warnning.setText("User Login successfully");
          // change scene to profile
          Parent loader = FXMLLoader.load(getClass().getResource("profilee-view.fxml"));
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.setScene(new Scene(loader));
          stage.show();
          //
        }
      }
    }


    @FXML
    private void register (ActionEvent event) throws IOException {
      Parent loader = FXMLLoader.load(getClass().getResource("registerr-view.fxml"));
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(loader));
      stage.show();
    }
  }
