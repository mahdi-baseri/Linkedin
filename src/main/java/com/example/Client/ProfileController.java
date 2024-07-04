package com.example.Client;

import com.example.linkedin.DataAccess.UserDatabase;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.linkedin.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import org.json.JSONObject;
import org.json.simple.JSONObject;

public class ProfileController {
    @FXML
    private Button profileBtn;
    @FXML
    private Button save;
    @FXML
    private VBox vbox;
    @FXML
    private Label warning;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLable;
    @FXML
    private Button addPhotoBtn;
    @FXML
    private TextField schoolTxt;
    @FXML
    private TextField degreeTxt;
    @FXML
    private TextField fieldOfStudyTxt;
    @FXML
    private TextField gradeTxt;
    @FXML
    private TextField activitiesTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField skillTxt;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    private String school;
    private String degree;
    private String fieldOfStudy;
    private String grade;
    private String activities;
    private String description;
    private String skill;
    private String endDate1;
    private String StartDate2;


    public void profileController(ActionEvent event){
        if(vbox.isVisible()){
            vbox.setVisible(false);
        }else {
            vbox.setVisible(true);
        }
    }

    public void addPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg" , "*.jfif"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);


            Circle clip = new Circle();
            clip.setCenterX(imageView.getFitWidth()/2);
            clip.setCenterY(imageView.getFitHeight()/2);
            clip.setRadius(imageView.getFitWidth()/2);

            imageView.setClip(clip);
        }
    }


    public void saveEducationInfo(ActionEvent event) {
        this.school = schoolTxt.getText();
        System.out.println(school);
        this.degree = degreeTxt.getText();
        this.fieldOfStudy = fieldOfStudyTxt.getText();
        this.grade = gradeTxt.getText();
        this.activities = activitiesTxt.getText();
        this.description = descriptionTxt.getText();
        this.skill = skillTxt.getText();
        this.endDate1 = endDate.toString();
        this.StartDate2 = startDate.toString();


        if ((school.equals("")) || (degree.equals("")) || (fieldOfStudy.equals("")) || (grade.equals("")) || (activities.equals("")) || (description.equals("")) || (skill.equals(""))) {
            warning.setTextFill(Color.RED);
            warning.setText("Please compelte all fields");

        } else {
            try {
                JSONObject json = new JSONObject();
                json.put("school", schoolTxt.getText());
                json.put("degree", degreeTxt.getText());
                json.put("fieldofstudy", fieldOfStudyTxt.getText());
                json.put("grade", gradeTxt.getText());
                json.put("descriptionActivity", activitiesTxt.getText());
                json.put("description", descriptionTxt.getText());
                json.put("skill", skillTxt.getText());
                System.out.println(json);
                URL url = new URL("http://localhost:8000" + "/education");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(json.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputline = null;
                    StringBuffer response1 = new StringBuffer();
                    while ((inputline = in.readLine()) != null) {
                        response1.append(inputline);
                    }
                    in.close();
                    String response = response1.toString();
                    System.out.println(response);

                 //  LoginPage.token = con.getHeaderField("JWT");
                  vbox.setVisible(false);

                if (response.equals("Education already exists")) {
                    warning.setText("Education already exists");
                } else if (response.equals("Unauthorized")) {
                    warning.setText("Unauthorized");
                } else if (response.equals("Invalid request")) {
                    warning.setText("Invalid request");
                } else {
                    warning.setText("Education added successfully");
                }


            } catch (Exception e) {
                warning.setText("connection failed");
                e.printStackTrace();
            }
        }
    }
}
