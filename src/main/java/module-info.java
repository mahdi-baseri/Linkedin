module com.example.linkedin {
  requires javafx.controls;
  requires javafx.fxml;
  //requires fontawesomefx;
  requires com.dlsc.formsfx;
  requires java.sql;
  requires java.base;
    requires jdk.httpserver;
    requires org.json;
    requires com.google.gson;
  exports com.example.Client;

  exports com.example.linkedin.Model;
  requires javafx.base;
  requires javafx.graphics;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.ikonli.core;
  requires de.jensd.fx.glyphs.fontawesome;
    requires jjwt.api;
  requires com.fasterxml.jackson.databind;
  requires java.net.http;
    requires json.simple;
    // add icon pack modules
  opens com.example.Client to javafx.fxml;

}