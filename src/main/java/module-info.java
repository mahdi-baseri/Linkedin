module com.example.linkedin {
  requires javafx.controls;
  requires javafx.fxml;

  requires com.dlsc.formsfx;
  requires java.sql;

  opens com.example.Client to javafx.fxml;
  exports com.example.Client;
  exports com.example.Client.Controller;
  opens com.example.Client.Controller to javafx.fxml;
}