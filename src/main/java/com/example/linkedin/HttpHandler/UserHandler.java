
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
        String path = exchange.getRequestURI().getPath();
        String[] splittedPath = path.split("/");
        String response = "";


            switch (request) {
                case "POST":
                    if (splittedPath.length == 2) { //user
                        JSONObject jsonObject = getJsonObject(exchange);
                        if (ValidJson(jsonObject)) {
                            try {
                                userController.createUser(jsonObject.getString("id"), jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getString("country"), jsonObject.getString("email"), jsonObject.getString("phonenumber"), jsonObject.getString("password"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            //   LoginPage.name = jsonObject.getString("firstname");
                           // LoginPage.lastName = jsonObject.getString("lastname");
                            response = "User added successfully";
                            exchange.sendResponseHeaders(200, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        } else {
                            response = "User info not valid";
                            exchange.sendResponseHeaders(400, response.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes());
                            os.close();
                        }
                    }
                    else {
                        response = "Invalid path";
                        exchange.sendResponseHeaders(409, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
            }
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

    public boolean ValidJson(JSONObject jsonObject) {
        if(jsonObject.has("id") &&jsonObject.has("firstname") && jsonObject.has("lastname") &&
                jsonObject.has("email") && jsonObject.has("password") && jsonObject.has("country") &&
                jsonObject.has("phonenumber")){
            return true ;
        }
        return false;
    }
}
