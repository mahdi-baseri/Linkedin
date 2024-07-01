package com.example.Client;

import com.example.linkedin.DataAccess.DatabaseConnector;
import com.example.linkedin.DataAccess.UserDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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


  public LoginController() throws SQLException {
    userDatabase = new UserDatabase();
    connection = DatabaseConnector.getConnection();
  }

  @FXML
  private void submit(ActionEvent event) {
    emailOfUser = email.getText();
    password = passField.getText();

    try {

      if ((email.getText().equals("")) || (passField.equals(""))) {
        warnning.setText("Please compelte fields");

      } else if (!email.getText().endsWith("@gmail.com")) {
        warnning.setText("please check your email");

      } else {
        //http://localhost:8000/login/email/password
        URL url = new URL("http://localhost:8000/" + "login/" + emailOfUser + "/" + password);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        if (responseCode == 200) {  //ok
          BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
          String inputline = null;
          StringBuffer response1 = new StringBuffer();
          while ((inputline = in.readLine()) != null) {
            response1.append(inputline);
          }
          in.close();

          // change scene to profile
          Parent loader = FXMLLoader.load(getClass().getResource("profile-view.fxml"));
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.setScene(new Scene(loader));
          stage.show();
          //

        } else if (responseCode == 404) {
          warnning.setText("User not found OR invalid password");
        } else if (responseCode == 400) {
          warnning.setText("Invalid Request");
        } else if (responseCode == 405) {
          warnning.setText("Methode Not Allowed");
        }

      }
    }catch(Exception e){
        warnning.setText("connection failed");
        e.printStackTrace();
      }
    }


    @FXML
    private void register (ActionEvent event) throws IOException {
      Parent loader = FXMLLoader.load(getClass().getResource("register-view.fxml"));
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(loader));
      stage.show();
    }
  }
