package com.example.linkedin.HttpHandler;

import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.linkedin.Utils.JwtController;

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
        user = new User();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] pathParts = path.split("/");
      // System.out.println(path);

        switch(method) {
            case "GET":
                if (pathParts.length == 4) { // login/email/password
                    String email = pathParts[2];
                    String password = pathParts[3];

                    try {
                        user = userDatabase.getUser(email , password);
                        if (user == null) {
                            response = "User not found OR invalid password";
                            exchange.sendResponseHeaders(404, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                        else {
                            String token = JwtController.createToken(email);
                            Headers responseHeaders = exchange.getResponseHeaders();
                            responseHeaders.add("JWT", token);
                            response = exchange.getResponseHeaders().toString();
                            exchange.sendResponseHeaders(200, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                    } catch (Exception e) {
                        response = "Error in login user";
                        exchange.sendResponseHeaders(500, response.length());
                        System.out.println("Error in login user");
                        e.printStackTrace();
                    }
                    finally {
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                } else {
                    response = "Invalid request";
                    exchange.sendResponseHeaders(400, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
                break;

           default:
                response = "Method not allowed";
                 exchange.sendResponseHeaders(405, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
        }
    }
}
