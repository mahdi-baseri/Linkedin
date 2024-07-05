package com.example.Client;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
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


    public void saveEducationInfo(ActionEvent event) throws IOException {
        this.school = schoolTxt.getText();
        this.degree = degreeTxt.getText();
        this.fieldOfStudy = fieldOfStudyTxt.getText();
        this.grade = gradeTxt.getText();
        this.activities = activitiesTxt.getText();
        this.description = descriptionTxt.getText();
        this.skill = skillTxt.getText();
        this.endDate1 = endDate.toString();
        this.StartDate2 = startDate.toString();


        if (!checkEmpty()){
            warning.setTextFill(Color.RED);
            warning.setText("Please compelte all fields");
        } else {
                JSONObject json = new JSONObject();
                json.put("school", schoolTxt.getText());
                json.put("degree", degreeTxt.getText());
                json.put("fieldofstudy", fieldOfStudyTxt.getText());
                json.put("grade", gradeTxt.getText());
                json.put("descriptionActivity", activitiesTxt.getText());
                json.put("description", descriptionTxt.getText());
                json.put("skill", skillTxt.getText());
                URL url = new URL("http://localhost:8000/education");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(json.toString().getBytes());
                os.flush();
                os.close();
                int responsecode = connection.getResponseCode() ;

                if (responsecode == 404) {
                    warning.setText("Education already exists");
                } else if (responsecode == 409) {
                    warning.setText("Invalid Path");
                }  else {
                    warning.setText("Education added successfully");
                    vbox.setVisible(false);
                }

        }
    }

    public Boolean checkEmpty(){
        if ((school.equals("")) || (degree.equals("")) || (fieldOfStudy.equals("")) || (grade.equals("")) || (activities.equals("")) || (description.equals("")) || (skill.equals(""))) {
            return false ;
        }
        return true ;
    }
}


