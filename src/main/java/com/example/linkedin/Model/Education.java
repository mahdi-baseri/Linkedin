package com.example.linkedin.Model;


import java.util.Date;

public class Education {
    private String userId;
    private String nameSchool;
    private String fieldStudy;
    private Date dateStart;
    private Date dateEnd;
    private String DescriptionActivity;
    private String Description;
    private String skills;
    private String degree ;
    private String grade ;
    private String email ;

    public Education(String nameSchool, String degree,String fieldStudy, String grade, String descriptionActivity, String description, String skills ,String email) {
        this.nameSchool = nameSchool;
        this.fieldStudy = fieldStudy;
       this.DescriptionActivity = descriptionActivity;
        this.Description = description;
        this.skills = skills;
        this.grade = grade;
        this.email = email ;
    }
    public Education(){

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNameSchool() {
        return nameSchool;
    }

    public void setNameSchool(String nameSchool) {
        this.nameSchool = nameSchool;
    }

    public String getFieldStudy() {
        return fieldStudy;
    }

    public void setFieldStudy(String fieldStudy) {
        this.fieldStudy = fieldStudy;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    public String getDegree(){
        return degree ;
    }
    public String getGrade(){
        return grade;
    }

    public String getDescriptionActivity() {
        return DescriptionActivity;
    }

    public void setDescriptionActivity(String descriptionActivity) {
        DescriptionActivity = descriptionActivity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setGrade(String grade) {
        this.grade = grade ;
    }

    public void setEmail(String email) {
        this.email = email ;
    }
    public String getEmail(){
        return email;
    }
}