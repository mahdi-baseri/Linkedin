package com.example.Client;

import com.example.linkedin.DataAccess.UserDatabase;
import java.sql.SQLException;
import java.util.WeakHashMap;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
  private Label emailWarning;
  @FXML
  private Label idWarning;
  @FXML
  private Label nameWarning;
  @FXML
  private Label lastnameWarning;
  private UserDatabase userDatabase;

  public RegisterController() throws SQLException {
    userDatabase = new UserDatabase();
  }

  @FXML
  private void checkRegister(ActionEvent event) throws SQLException {
    checkEmail(email.getText());
    checkId(id.getText());
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
    }
  }
}
