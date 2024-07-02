package com.example;

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
    InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
    HttpServer server = HttpServer.create(new InetSocketAddress(inetAddress, Integer.parseInt("8000")) , 0);
    server.createContext("/user" , new UserHandler()); // sign up
    server.createContext("/login", new LoginHandler()); //for login
    server.start();
}

}


