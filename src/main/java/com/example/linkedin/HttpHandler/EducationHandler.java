package com.example.linkedin.HttpHandler;

import com.example.Client.LoginPage;
import com.example.linkedin.Controller.EducationController;
import com.example.linkedin.Controller.UserController;
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

    public EducationHandler() throws SQLException {
        educationController = new EducationController();
        userController = new UserController();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String request = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] splittedPath = path.split("/");
        String response = "";
        String email = LoginPage.email;

        switch (request) {
            case "POST":
                if (splittedPath.length == 2) {
                    JSONObject jsonObject = getJsonObject(exchange);
                    if (!ValidJson(jsonObject)) {
                        response = "Invalid request";
                        exchange.sendResponseHeaders(400, response.length());
                    }
                    try {
                        if (educationController.getEducationByEmailAndSchool(email, jsonObject.getString("school")) == null) {
                            educationController.createEducation(jsonObject.getString("school"), jsonObject.getString("degree"), jsonObject.getString("fieldofstudy"), jsonObject.getString("grade"), jsonObject.getString("descriptionActivity"), jsonObject.getString("description"), jsonObject.getString("skill"), email);
                            response = "Education added successfully";
                            exchange.sendResponseHeaders(200, response.length());
                        } else {
                            response = "Education already exists";
                            exchange.sendResponseHeaders(404, response.length());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    response = "Invalid Path";
                    exchange.sendResponseHeaders(409, response.length());
                }
        }
    }

   public boolean ValidJson(JSONObject jsonObject) {
        if(jsonObject.has("school") && jsonObject.has("degree") && jsonObject.has("fieldofstudy") && jsonObject.has("grade") && jsonObject.has("descriptionActivity") && jsonObject.has("description") && jsonObject.has("skill")){
            return true ;
        }
        return false;
    }

   public JSONObject getJsonObject(HttpExchange exchange) throws IOException {
        InputStream requestBody = null;
            requestBody = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            return new JSONObject(body.toString());
    }
}








