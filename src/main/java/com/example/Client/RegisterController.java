package com.example.Client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class RegisterController {

  @FXML
  private TextField email;
  @FXML
  private TextField id;
  @FXML
  private TextField name;
  @FXML
  private TextField lastName;
  @FXML
  private TextField phoneNumber;
  @FXML
  private PasswordField password;
  @FXML
  private PasswordField confirmPassword;
  @FXML
  private TextField country;

  @FXML
  private Button registerBtn;


  @FXML
  private Label emailWarning;
  @FXML
  private Label idWarning;
  @FXML
  private Label nameWarning;
  @FXML
  private Label lastNameWarning;
  @FXML
  private Label phoneNumberWarning;
  @FXML
  private Label passwordWarning;
  @FXML
  private Label confirmPasswordWarning;
  @FXML
  private Label countryWarning;
  @FXML
  private Label warning;



  @FXML
  private void checkRegister(ActionEvent event) throws SQLException, IOException {
    checkEmpty();
    checkEmail(email.getText());
    checkPhoneNumber(phoneNumber.getText());

    if (isValidPassword() && checkEmpty()) {
        JSONObject json = new JSONObject();
        json.put("id", id.getText());
        json.put("firstname", name.getText());
        json.put("lastname", lastName.getText());
        json.put("email", email.getText());
        json.put("password", password.getText());
        json.put("country", country.getText());
        json.put("phonenumber", phoneNumber.getText());

        URL url = new URL("http://localhost:8000/user");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(json.toString().getBytes());
        os.flush();
        os.close();
        int responsecode = connection.getResponseCode() ;

        if (responsecode == 400) {
          warning.setText("User info not valid");
        } else if (responsecode == 409) {
          warning.setText("Invalid Path");
        } else {
          Parent loader = FXMLLoader.load(getClass().getResource("login-page.fxml"));
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          stage.setScene(new Scene(loader));
          stage.show();
        }
    }
  }

  private boolean checkEmpty() {
    boolean isFormValid = true;

    if (id.getText().isEmpty()) {
      idWarning.setText("Empty");
      idWarning.setTextFill(Color.RED);
      isFormValid = false ;
    }
    else {
      idWarning.setText("Valid");
      idWarning.setTextFill(Color.BLUE);
    }

    if (name.getText().isEmpty()) {
      nameWarning.setText("Empty");
      nameWarning.setTextFill(Color.RED);
      isFormValid = false;
    } else {
      nameWarning.setText("Valid");
      nameWarning.setTextFill(Color.BLUE);
    }

    if (lastName.getText().isEmpty()) {
      lastNameWarning.setText("Empty");
      lastNameWarning.setTextFill(Color.RED);
      isFormValid = false;
    } else {
      lastNameWarning.setText("Valid");
      lastNameWarning.setTextFill(Color.BLUE);
    }

    if (confirmPassword.getText().isEmpty()) {
      confirmPasswordWarning.setText("Empty");
      confirmPasswordWarning.setTextFill(Color.RED);
      isFormValid = false;
    } else {
      confirmPasswordWarning.setText("Valid");
      confirmPasswordWarning.setTextFill(Color.BLUE);
    }

    if (password.getText().isEmpty()) {
      passwordWarning.setText("Empty");
      passwordWarning.setTextFill(Color.RED);
      isFormValid = false;
    } else {
      passwordWarning.setText("Valid");
      passwordWarning.setTextFill(Color.BLUE);
    }

    if (country.getText().isEmpty()) {
      countryWarning.setText("Empty");
      countryWarning.setTextFill(Color.RED);
      isFormValid = false;
    } else {
      countryWarning.setText("Valid");
      countryWarning.setTextFill(Color.BLUE);
    }

    return isFormValid;
  }


  private boolean isValidPassword() {
    String passwordStr = password.getText();
    String confirmPasswordStr = confirmPassword.getText();

    Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
    Matcher matcher = pattern.matcher(passwordStr);
    Matcher matcher2 = pattern.matcher(confirmPasswordStr);

    boolean isPasswordValid = matcher.matches();
    boolean isConfirmPasswordValid = matcher2.matches();
    boolean doPasswordsMatch = passwordConfirm();

    if (isPasswordValid && isConfirmPasswordValid && doPasswordsMatch) {
      passwordWarning.setText("Valid");
      confirmPasswordWarning.setText("Valid");
      passwordWarning.setTextFill(Color.BLUE);
      confirmPasswordWarning.setTextFill(Color.BLUE);
      return true;
    } else {
      if (password.getText().isEmpty() && confirmPassword.getText().isEmpty()) {
        passwordWarning.setText("Empty");
        confirmPasswordWarning.setText("Empty");
        passwordWarning.setTextFill(Color.RED);
        confirmPasswordWarning.setTextFill(Color.RED);
        return false;
      } else {
        passwordWarning.setText("Invalid");
        passwordWarning.setTextFill(Color.RED);
        confirmPasswordWarning.setText("Invalid");
        confirmPasswordWarning.setTextFill(Color.RED);
        return false;
      }
    }
  }


private boolean passwordConfirm() {
  if (!password.getText().equals(confirmPassword.getText())) {
    confirmPasswordWarning.setText("ConfirmPassword is incorrect");
    confirmPasswordWarning.setTextFill(Color.RED);
    return false;
  } else {
    confirmPasswordWarning.setText("Valid");
    passwordWarning.setText("Valid");
    confirmPasswordWarning.setTextFill(Color.BLUE);
    passwordWarning.setTextFill(Color.BLUE);
    return true;
  }
}

  private void checkEmail(String email) throws SQLException {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (email.isEmpty()) {
      emailWarning.setText("Empty");
      emailWarning.setTextFill(Color.RED);

    } else if (!pattern.matcher(email).matches()) {
      emailWarning.setText("Invalid");
      emailWarning.setTextFill(Color.RED);

    }
    else {
      emailWarning.setText("Valid");
      emailWarning.setTextFill(Color.BLUE);
    }
  }

  private void checkPhoneNumber(String number){
    String regex = "^(?:(?:(?:\\+?|00)(98))|(0))?((?:90|91|92|93|99)[0-9]{8})$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(number);
    if (phoneNumber.getText().isEmpty()){
      phoneNumberWarning.setText("Empty");
      phoneNumberWarning.setTextFill(Color.RED);
    }else if (!matcher.matches()){
      phoneNumberWarning.setText("Invalid");
      phoneNumberWarning.setTextFill(Color.RED);
    }
    else {

      phoneNumberWarning.setText("Valid");
      phoneNumberWarning.setTextFill(Color.BLUE);
    }
  }
}
