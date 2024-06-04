package com.example.linkedin.HttpHandler;

import com.example.linkedin.Controller.MessageController;
import com.example.linkedin.Controller.UserController;
import com.example.linkedin.Model.Message;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.SQLException;
import org.json.JSONObject;
import org.json.*;
import com.google.gson.Gson;

public class MessageHandler implements HttpHandler {
    private final MessageController messageController;

    public MessageHandler() throws SQLException {
        this.messageController = new MessageController();
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        String[] splittedPath = path.split("/");
        switch (method) {
            case "GET":
                if(splittedPath.length == 4){  // path : localhost/direct/senderPerson/receiverPerson
                    try {
                        response = messageController.getMessages(splittedPath[2] , splittedPath[3]);// (sender , receiver)
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "POST": // path : localhost/direct/id/senderperson/receiverperson/text
                InputStream requestBody = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }
                requestBody.close();
                String newMessage = body.toString();
                Gson gson = new Gson();
                Message message = gson.fromJson(newMessage , Message.class);
                try {
                    messageController.addMessage(message.getId() , message.getSender() , message.getReceiver() , message.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "DELETE": //path : localhost/direct/id
                if(splittedPath.length == 3){
                    String id = splittedPath[2];
                    try {
                        messageController.deleteMessage(id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                response = "The request to DELETE the message was successfully completed";
                break ;
            default:
                break;
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}