package com.example;


import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.HttpHandler.UserHandler;
import com.example.linkedin.Model.User;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Server {

  public static void main(String[] args) throws SQLException, IOException {
    User user = new User("rezaesfandi", "reza", "esfandiari", "rezaesfandiari84@gmail.com", "09371962511","123");
    UserDatabase userDatabase = new UserDatabase();
    userDatabase.addUser(user);

    HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
    server.createContext("/user" , new UserHandler());
    server.start();
}
}


