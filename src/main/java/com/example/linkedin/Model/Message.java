package com.example.linkedin.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    private String id;

    private String sender;

    private String receiver;

    private String text;

    public Message () {

    }

    public Message(String id, String sender, String receiver, String text) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public Message(String sender, String receiver, String text) {
        this.id = sender + System.currentTimeMillis();
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}