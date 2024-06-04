package com.example.linkedin.HttpHandler;
import com.example.linkedin.Controller.UserController;
import com.example.linkedin.Model.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;

public class UserHandler implements HttpHandler {
    private final UserController userController;

    public UserHandler() throws SQLException {
        this.userController = new UserController();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splittedPath = path.split("/");
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
            case "POST" :
                InputStream requestBody = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
                requestBody.close();
                String newUser = body.toString();
                Gson gson = new Gson() ;
                User user = gson.fromJson(newUser , User.class);
                try {
                    userController.createUser(user.getId() , user.getName() , user.getLastName(),user.getEmail() , user.getPhoneNumber() , user.getPassword());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                response = "done";
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
