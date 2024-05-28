package com.example.linkedin.HttpHandler;
import com.example.linkedin.Controller.UserController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.*;
import java.sql.SQLException;
public class UserHandler implements HttpHandler {
    private final UserController userController;

    public UserHandler() throws SQLException {
        this.userController = new UserController();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        System.out.println("the methode is :"+method);
        String path = exchange.getRequestURI().getPath();
        System.out.println("the path is :"+path);
        String response = "";
        String[] splittedPath = path.split("/");
        System.out.println(splittedPath.length);

        switch (method) {
            case "GET":
                if (splittedPath.length == 2) {
                    try {
                        response = userController.getUsers();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    String userId = splittedPath[splittedPath.length - 1];
                    try {
                        response = userController.getUserById(userId);
                        if (response == null)
                            response = "No User";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "PUT":
                response = "This is the response for users' PUT request.";
                break;

            case "DELETE":
                if (splittedPath.length != 2) {
                    String userId = splittedPath[splittedPath.length - 1];
                    try {
                        userController.deleteUser(userId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                response = "This is the response for users' DELETE request.\nDone!";
                break;

            default:
                break;
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
