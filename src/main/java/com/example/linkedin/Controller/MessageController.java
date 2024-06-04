package com.example.linkedin.Controller;

import com.example.linkedin.DataAccess.MessageDAO;
import com.example.linkedin.Model.Message;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;

public class MessageController {
    private final MessageDAO messageDAO;

    public MessageController() throws SQLException {
        messageDAO = new MessageDAO();
    }

    public void addMessage(String id, String sender, String receiver, String text) throws SQLException {
        messageDAO.saveMessage(new Message(id, sender, receiver, text));
    }

    public String getMessages(String u1, String u2) throws SQLException {
        ArrayList<Message> messages = messageDAO.getMessages(u1, u2);
        Gson gson = new Gson();
        String response = gson.toJson(messages);
        return response;
    }

    public void deleteMessage(String id) throws SQLException {
        messageDAO.deleteMessage(id);
    }

    public void deleteAll() throws SQLException {
        messageDAO.deleteAll();
    }

}