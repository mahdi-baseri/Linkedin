package com.example.linkedin.HttpHandler;

import com.example.linkedin.Controller.EducationController;
import com.example.linkedin.Controller.UserController;
import com.example.linkedin.DataAccess.UserDatabase;
import com.example.linkedin.Model.User;
import com.example.linkedin.Utils.JwtController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class EducationHandler implements HttpHandler {

    public UserController userController;
    public EducationController educationController;
    public UserDatabase userDatabase ;
    public User user;

    public EducationHandler() throws SQLException {
        educationController = new EducationController();
        userController = new UserController();
        userDatabase = new UserDatabase();
        user = new User();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String request = exchange.getRequestMethod();
        System.out.println(request);
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String response = "";

        try {
            try {
                String email = JwtController.verifyToken(exchange);
                if (email == null) {
                    response = "Unauthorized";
                    exchange.sendResponseHeaders(401, response.length());
                    sendResponse(exchange, response);
                    return;
                }
            } catch (Exception e) {
                response = "Unauthorized";
                exchange.sendResponseHeaders(401, response.length());
                sendResponse(exchange, response);
                return;
            }
            switch (request) {
                case "POST":
                    response = handlePostRequest(exchange, educationController, pathParts);
                    break;
                default:
                    response = "Method not allowed";
                    exchange.sendResponseHeaders(405, response.length());
                    sendResponse(exchange, response);
            }
            sendResponse(exchange, response);
        } catch (Exception e) {
            response = "Internal server error";
            exchange.sendResponseHeaders(500, response.length());
            sendResponse(exchange, response);
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
            return new JSONObject(body.toString());
        }
    }

    private boolean isValidJson(JSONObject jsonObject) {
        return jsonObject.has("school") && jsonObject.has("degree") && jsonObject.has("fieldofstudy") && jsonObject.has("grade") && jsonObject.has("descriptionActivity") && jsonObject.has("description") && jsonObject.has("skill");
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
        exchange.sendResponseHeaders(200, response.length());
    }



    private String handlePostRequest(HttpExchange exchange, EducationController educationController, String[] pathParts) throws SQLException, IOException {
        String response = "";
        if (pathParts.length == 2) {
            System.out.println("inja");
            String email = JwtController.verifyToken(exchange);
            System.out.println("ziresh");
            JSONObject jsonObject = getJsonObject(exchange);
            System.out.println(jsonObject);
            if (!isValidJson(jsonObject)) {
                response = "Invalid request";
                exchange.sendResponseHeaders(400, response.length());
                sendResponse(exchange, response);
                return response;
            }
            if (email != null) {
                if (educationController.getEducationByEmailAndSchool(email, jsonObject.getString("school")) == null) {
                    educationController.createEducation(
                            jsonObject.getString("school"),
                            jsonObject.getString("degree"),
                            jsonObject.getString("fieldofstudy"),
                            jsonObject.getString("grade"),
                            jsonObject.getString("discriptionActivity"),
                            jsonObject.getString("description"),
                            jsonObject.getString("skill"),
                            email);
                    response = "Education added successfully";
                    exchange.sendResponseHeaders(200, response.length());
                } else {
                    response = "Education already exists";
                    exchange.sendResponseHeaders(409, response.length());
                }
            } else {
                response = "Unauthorized";
                exchange.sendResponseHeaders(401, response.length());
            }
        } else {
            response = "Invalid request";
            exchange.sendResponseHeaders(400, response.length());
        }
        return response;
    }

}
