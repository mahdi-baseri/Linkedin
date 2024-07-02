
package com.example.linkedin.HttpHandler;
import com.example.linkedin.Controller.UserController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.*;
import java.sql.SQLException;


public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        UserController userController;

        try {
            userController = new UserController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String request = exchange.getRequestMethod();
        System.out.println(request);
        String path = exchange.getRequestURI().getPath();
        String[] splittedPath = path.split("/");
        String response = "";

        try {
            switch (request) {
                case "GET":
                    if (splittedPath.length == 3) {
                        if (splittedPath[2].equals("all")) { // user/all
                            response = userController.getUsers();
                            exchange.sendResponseHeaders(200, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        } else { // user/email
                            response = userController.getUserByEmail(splittedPath[2]);
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

                case "POST":
                    if (splittedPath.length == 2) { //user
                        JSONObject jsonObject = getJsonObject(exchange);
                        System.out.println(jsonObject);
                        if (isValidJson(jsonObject)) {
                            userController.createUser(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("firstname"),
                                    jsonObject.getString("lastname"),
                                    jsonObject.getString("country"),
                                    jsonObject.getString("email"),
                                    jsonObject.getString("phonenumber"),
                                    jsonObject.getString("password"));

                            response = "User added successfully";
                            exchange.sendResponseHeaders(200, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        } else {
                            response = "Bad request";
                            exchange.sendResponseHeaders(400, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                    }
                    else {
                        response = "Invalid path";
                        exchange.sendResponseHeaders(400, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                default:
                    response = "Method not allowed";
                    exchange.sendResponseHeaders(405, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
            }
        } catch (Exception e) {
            response = "Internal server error";
            exchange.sendResponseHeaders(500, response.length());
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonObject(HttpExchange exchange) throws IOException {
        try (InputStream requestBody = exchange.getRequestBody();
             BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody))) {
            StringBuilder body = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            System.out.println(body);
            return new JSONObject(body.toString());
        }
    }

    private static boolean isValidJson(JSONObject jsonObject) {
        return jsonObject.has("id") &&jsonObject.has("firstname") && jsonObject.has("lastname") &&
                jsonObject.has("email") && jsonObject.has("password") && jsonObject.has("country") &&
                jsonObject.has("phonenumber");
    }
}
