package com.example.linkedin.HttpHandler;

import com.example.Client.LoginPage;
import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import com.example.linkedin.Controller.UserController;

import java.sql.SQLException;

public class LoginHandler implements HttpHandler {

   public UserController userController ;
   public UserDatabase userDatabase ;
   public User user;

    public LoginHandler() throws SQLException {
        userController = new UserController();
        userDatabase = new UserDatabase();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splittedPath = path.split("/");

        switch(method) {
            case "GET":
                if (splittedPath.length == 4) {
                    String email = splittedPath[2];
                    String password = splittedPath[3];

                    try {
                        user = userController.getUser(email , password);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (user == null) {
                            response = "User not found.Incorrect password or email";
                            exchange.sendResponseHeaders(409, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                        else {
                            LoginPage.email = email ;
                            response = " User Login successfully" ;
                            exchange.sendResponseHeaders(200, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                } else {
                    response = "Invalid path";
                    exchange.sendResponseHeaders(400, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
                break;
        }
    }
}
