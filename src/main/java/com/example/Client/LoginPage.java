package com.example.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage extends Application {

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