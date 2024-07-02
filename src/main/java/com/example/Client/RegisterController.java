package com.example.Client;

import com.example.linkedin.DataAccess.UserDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.linkedin.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import org.json.JSONObject;
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
  private UserDatabase userDatabase;
  private boolean validEmail , validId , validPhoneNumber , validEmpty;

  public RegisterController() throws SQLException {
    userDatabase = new UserDatabase();
    validEmail =false;
    validId = false;
    validPhoneNumber = false;
    validEmpty = false;

  }


  @FXML
  private void checkRegister(ActionEvent event) throws SQLException{

    checkEmail(email.getText());
    checkId(id.getText());
    checkPhoneNumber(phoneNumber.getText());
    checkEmpty();
//    if (!name.getText().isEmpty() && !lastName.getText().isEmpty() && !password.getText().isEmpty() ){
//      validEmpty = true;
//    }
//    User user = new User(id.getText(),name.getText(),lastName.getText(),email.getText(),phoneNumber.getText(),password.getText());
//    if (validId && validEmpty && validPhoneNumber && validEmail){
//      userDatabase.addUser(user);
//      if (userDatabase.idExists(id.getText())){
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setHeaderText("Registered successfully " + name.getText());
//        Optional<ButtonType> result = alert.showAndWait();
//        if(!result.isPresent()){
//
//        }
//        // alert is exited, no button has been pressed.
//        else if(result.get() == ButtonType.OK){
//          Parent loader = FXMLLoader.load(getClass().getResource("login-page.fxml"));
//          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//          stage.setScene(new Scene(loader));
//          stage.show();
//        }
//        //oke button is pressed
//        else if(result.get() == ButtonType.CANCEL){
//
//        }
//          // cancel button is pressed
//
//      }
//    }
        if(checkEmpty() && isValidPassword()) {
          try {
            JSONObject json = new JSONObject();
            json.put("id" , id.getText());
            json.put("firstname", name.getText());
            json.put("lastname", lastName.getText());
            json.put("email", email.getText());
            json.put("password", password.getText());
            json.put("country", country.getText());
            json.put("phonenumber", phoneNumber.getText());
            System.out.println(json);
            URL url = new URL("http://localhost:8000/user");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String inputline = null;
            StringBuffer response1 = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
              response1.append(inputline);
            }
            in.close();
            String response = response1.toString();
            System.out.println(response);

            if (response.equals("Method not allowed")) {
              warning.setText("Method not allowed");
            } else if (response.equals("Internal server error")) {
              warning.setText("Internal server error");
            } else if (response.equals("Bad request")) {
              warning.setText("Bad request");
            } else if (response.equals("Invalid path")) {
              warning.setText("Invalid path");
            } else {
              Parent loader = FXMLLoader.load(getClass().getResource("login-page.fxml"));
              Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
              stage.setScene(new Scene(loader));
              stage.show();
            }

          } catch (Exception e) {
            warning.setText("connection failed");
          }
        }
  }

  private boolean checkEmpty() {
    boolean isFormValid = true;

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

    if (confirmPasswordWarning.getText().isEmpty()) {
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
      passwordWarning.setText( "Invalid");
      passwordWarning.setTextFill(Color.RED);
      confirmPasswordWarning.setText("Invalid");
      confirmPasswordWarning.setTextFill(Color.RED);
      return false;
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
    Pattern pat = Pattern.compile(emailRegex);
    if (email.isEmpty()) {
      emailWarning.setText("Empty");
      emailWarning.setTextFill(Color.RED);

    } else if (!pat.matcher(email).matches()) {
      emailWarning.setText("Invalid");
      emailWarning.setTextFill(Color.RED);

    }
//    else if (userDatabase.emailExists(email)) {
//      emailWarning.setText("Email Exists");
//      emailWarning.setTextFill(Color.RED);
//    }
    else {
      emailWarning.setText("Valid");
      emailWarning.setTextFill(Color.BLUE);
      validEmail = true;

    }
  }

  private void checkId(String id) throws SQLException {
    if (id.isEmpty()) {
      idWarning.setText("Empty");
      idWarning.setTextFill(Color.RED);
    }
//    else if (userDatabase.idExists(id)) {
//      idWarning.setText("Id Exists");
//      idWarning.setTextFill(Color.RED);
//    }
    else {
      idWarning.setText("Valid");
      idWarning.setTextFill(Color.BLUE);
      validId = true;
    }
  }

  private void checkPhoneNumber(String number){
//    String regex = "/^(?:(?:(?:\\+?|00)(98))|(0))?((?:90|91|92|93|99)[0-9]{8})$";
    String regex = "^(?:(?:(?:\\+?|00)(98))|(0))?((?:90|91|92|93|99)[0-9]{8})$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(number);
    if (phoneNumber.getText().isEmpty()){
      phoneNumberWarning.setText("Empty");
      phoneNumberWarning.setTextFill(Color.RED);
    }else if (!matcher.matches()){
      phoneNumberWarning.setText("Inalid");
      phoneNumberWarning.setTextFill(Color.RED);
    }
    else {
      phoneNumberWarning.setText("Valid");
      phoneNumberWarning.setTextFill(Color.BLUE);
      validPhoneNumber = true;
    }
  }
}
