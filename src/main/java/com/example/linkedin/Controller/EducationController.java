package com.example.linkedin.Controller;

import com.example.linkedin.DataAccess.EducationDAO;
import com.example.linkedin.Model.Education;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;

public class EducationController {
    private static EducationDAO educationDAO;

    public EducationController() throws SQLException {
        educationDAO = new EducationDAO();
    }

    public void createEducation(String school, String degree, String fieldOfStudy, String grade, String descriptionActivity, String description , String skill, String email) throws SQLException {
        Education education = new Education(school, degree, fieldOfStudy, grade, descriptionActivity, description, skill, email);
        educationDAO.saveEducationDetail(education, email);
    }

    public String getEducationByEmailAndSchool(String email, String school) throws SQLException, JsonProcessingException {
        Education education = educationDAO.getEducation(email, school);
        if (education  == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(education);
    }

}
