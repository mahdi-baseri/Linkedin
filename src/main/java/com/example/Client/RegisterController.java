package com.example.Client;

import com.example.linkedin.DataAccess.UserDatabase;

import java.io.IOException;
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
  private UserDatabase userDatabase;
  private Label warnning;
  private boolean validEmail , validId , validPhoneNumber , validEmpty;
  public RegisterController() throws SQLException {
    userDatabase = new UserDatabase();
    validEmail =false;
    validId = false;
    validPhoneNumber = false;
    validEmpty = false;

  }

  @FXML
  private void checkRegister(ActionEvent event) throws SQLException, IOException {
    checkEmail(email.getText());
    checkId(id.getText());
    checkPhoneNumber(phoneNumber.getText());
    checkEmpty();
    if (!name.getText().isEmpty() && !lastName.getText().isEmpty() && !password.getText().isEmpty() ){
      validEmpty = true;
    }
    User user = new User(id.getText(),name.getText(),lastName.getText(),email.getText(),phoneNumber.getText(),password.getText());
    if (validId && validEmpty && validPhoneNumber && validEmail){
      userDatabase.addUser(user);
      if (userDatabase.idExists(id.getText())){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Registered successfully " + name.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){

        }
        // alert is exited, no button has been pressed.
        else if(result.get() == ButtonType.OK){
          Parent loader = FXMLLoader.load(getClass().getResource("login-page.fxml"));
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

          stage.setScene(new Scene(loader));
          stage.show();
        }
        //oke button is pressed
        else if(result.get() == ButtonType.CANCEL){

        }
          // cancel button is pressed

      }
    }
  }
private void checkEmpty(){
  if (name.getText().isEmpty()){
    nameWarning.setText("Empty");
    nameWarning.setTextFill(Color.RED);
  }else {
    nameWarning.setText("Valid");
    nameWarning.setTextFill(Color.GREEN);
  }
  if (lastName.getText().isEmpty()){
    lastNameWarning.setText("Empty");
    lastNameWarning.setTextFill(Color.RED);
  }else {
    lastNameWarning.setText("Valid");
    lastNameWarning.setTextFill(Color.GREEN);
  } if (password.getText().isEmpty()){
    passwordWarning.setText("Empty");
    passwordWarning.setTextFill(Color.RED);
  }else {
    passwordWarning.setText("Valid");
    passwordWarning.setTextFill(Color.GREEN);
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
      emailWarning.setText("Not Valid");
      emailWarning.setTextFill(Color.RED);

    } else if (userDatabase.emailExists(email)) {
      emailWarning.setText("Email Exists");
      emailWarning.setTextFill(Color.RED);
    } else {
      emailWarning.setText("Valid");
      emailWarning.setTextFill(Color.GREEN);
      validEmail = true;

    }
  }

  private void checkId(String id) throws SQLException {
    if (id.isEmpty()) {
      idWarning.setText("Empty");
      idWarning.setTextFill(Color.RED);
    } else if (userDatabase.idExists(id)) {
      idWarning.setText("Id Exists");
      idWarning.setTextFill(Color.RED);
    } else {
      idWarning.setText("Valid");
      idWarning.setTextFill(Color.GREEN);
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
      phoneNumberWarning.setText("Not Valid");
      phoneNumberWarning.setTextFill(Color.RED);
    }
    else {
      phoneNumberWarning.setText("Valid");
      phoneNumberWarning.setTextFill(Color.GREEN);
      validPhoneNumber = true;
    }
  }
}
