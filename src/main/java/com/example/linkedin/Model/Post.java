package com.example.linkedin.Model;

import java.sql.Date;
import java.util.ArrayList;

public class Post {

    private String id;
    private String ownerId;
    private String text;
    private ArrayList<String> mediaPaths;
    private ArrayList<User> likes;
    private ArrayList<String> comments;
    private Date createdAt;


    public Post(String id, String ownerId, String text, ArrayList<String> mediaPaths, ArrayList<User> likes, ArrayList<String> comments, Date createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.text = text;
        this.mediaPaths = mediaPaths;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
    }
    public Post(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<String> getMediaPaths() {
        return mediaPaths;
    }

    public void setMediaPaths(ArrayList<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    public void setMediaPaths(String[] mediaPaths) {
        this.mediaPaths = new ArrayList<>();
        if (mediaPaths != null) {
            for (String mediaPath : mediaPaths) {
                this.mediaPaths.add(mediaPath);
            }
        }
    }

    public void addLike(User user) {
        likes.add(user);
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public void addComment(String commentText, User user) {
        String comment = user.getName()+" "+ user.getLastName() + ": " + commentText;
        comments.add(comment);
    }


}