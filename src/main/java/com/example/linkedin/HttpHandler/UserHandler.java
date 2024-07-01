
package com.example.linkedin.HttpHandler;
import com.example.linkedin.Controller.UserController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.*;
import java.sql.SQLException;
import org.json.JSONObject;

public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        UserController userController = null;
        try {
            userController = new UserController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String request = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] splittedPath = path.split("/");
        String response = "";

        try {
            switch (request) {
                case "GET":
                    response = handleGetRequest(exchange, userController, splittedPath);
                    break;
               // case "POST":
                //    response = handlePostRequest(exchange, userController, splittedPath);
                   // break;
               // case "DELETE":
                 //   response = handleDeleteRequest(exchange, userController, splittedPath);
                  //  break;
                default:
                    response = "Method not allowed";
                    exchange.sendResponseHeaders(405, response.length());
            }
        } catch (Exception e) {
            response = "Internal server error";
            exchange.sendResponseHeaders(500, response.length());
            e.printStackTrace();
        } finally {
            sendResponse(exchange, response);
        }
    }

    private String handleGetRequest(HttpExchange exchange, UserController userController, String[] splittedPath) throws SQLException, IOException {
        String response = "";
        if (splittedPath.length == 3) {
            if (splittedPath[2].equals("all")) { // user/all
                response = userController.getUsers();
                exchange.sendResponseHeaders(200, response.length());
            } else { // user/email
                response = userController.getUserByEmail(splittedPath[2]);
                exchange.sendResponseHeaders(200, response.length());
            }
        } else {
            response = "Invalid path";
            exchange.sendResponseHeaders(400, response.length());
        }
        return response;
    }

  /*  private String handlePostRequest(HttpExchange exchange, UserController userController, String[] splittedPath) throws IOException, SQLException, DuplicateUserException {
        String response = "";
        if (pathParts.length == 2) {
            JSONObject jsonObject = getJsonObject(exchange);
            if (isValidJson(jsonObject)) {
                userController.createUser(
                        jsonObject.getString("firstname"),
                        jsonObject.getString("lastname"),
                        jsonObject.getString("email"),
                        jsonObject.getString("password"),
                        jsonObject.getString("country"),
                        jsonObject.getString("city")
                        new Date(jsonObject.getLong("birthday"));
                response = "User added successfully";
                exchange.sendResponseHeaders(200, response.length());
            } else {
                response = "Bad request";
                exchange.sendResponseHeaders(400, response.length());
            }
        } else {
            response = "Invalid path";
            exchange.sendResponseHeaders(400, response.length());
        }
        return response;
    }
    */



  /*  private String handleDeleteRequest(HttpExchange exchange, UserController userController, String[] splittedPath) throws SQLException, UserNotExistException, IOException {
        String response = "";
        if (pathParts.length == 3) {
            userController.deleteUserByEmail(splittedPath[2]);
            response = "User deleted successfully";
            exchange.sendResponseHeaders(200, response.length());
        } else {
            response = "Invalid path";
            exchange.sendResponseHeaders(400, response.length());
        }
        return response;
    }

   */

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

    private static boolean isValidJson(JSONObject jsonObject) {
        return jsonObject.has("firstname") && jsonObject.has("lastname") &&
                jsonObject.has("email") && jsonObject.has("password") &&
                jsonObject.has("country") && jsonObject.has("city");
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        exchange.close();
    }
}
