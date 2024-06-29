module com.example.linkedin {
  requires javafx.controls;
  requires javafx.fxml;
  //requires fontawesomefx;
  requires com.dlsc.formsfx;
  requires java.sql;
    requires jdk.httpserver;
    requires jackson.annotations;
    requires org.json;
    requires com.google.gson;
  exports com.example.Client;
  requires javafx.base;
  requires javafx.graphics;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.ikonli.core;
  requires de.jensd.fx.glyphs.fontawesome;
  // add icon pack modules
  opens com.example.Client to javafx.fxml;

}