module com.example.linkedin {
  requires javafx.controls;
  requires javafx.fxml;

  requires com.dlsc.formsfx;
  requires java.sql;
    requires jdk.httpserver;
    requires jackson.annotations;
    requires org.json;
    requires com.google.gson;

    opens com.example.Client to javafx.fxml;
  exports com.example.Client;

}