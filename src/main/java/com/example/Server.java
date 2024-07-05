package com.example;

import com.example.linkedin.HttpHandler.EducationHandler;
import com.example.linkedin.HttpHandler.LoginHandler;
import com.example.linkedin.HttpHandler.UserHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Server {

  public static void main(String[] args) throws IOException, SQLException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
    server.createContext("/education" , new EducationHandler());
    server.createContext("/user" , new UserHandler()); // sign up
    server.createContext("/login", new LoginHandler()); // login
    server.start();
}

}


