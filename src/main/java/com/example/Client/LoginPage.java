package com.example.Client;

import com.example.linkedin.Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Date;

public class LoginPage extends Application {
//  public static User loggedin_user = new User("reza1384", "reza", "esfandiari", "iran", "rezaesfandiari03@gmail.com", "09304751024", "reza4831");
//  public static String token = "";
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-page.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 700, 500);
    stage.setTitle("Lnkedin!");
    Image image = new Image("file:download.png");
    stage.getIcons().add(image);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}