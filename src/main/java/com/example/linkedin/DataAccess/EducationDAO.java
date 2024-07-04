package com.example.linkedin.DataAccess;

import com.example.linkedin.Model.Education;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducationDAO {
    private final Connection connection = DatabaseConnector.getConnection();

    public EducationDAO() throws SQLException {
        createEducationTable();
    }

    public void createEducationTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS educations (nameschool VARCHAR(255) NOT NULL, degree VARCHAR(255) NOT NULL, fieldofstudy VARCHAR(255) NOT NULL, grade VARCHAR(255) NOT NULL, descriptionActivity VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, skill VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL)");
        statement.executeUpdate();
    }
    public void saveEducationDetail(Education education, String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO educations VALUES (?,?,?,?,?,?,?,?)");

        statement.setString(1, education.getNameSchool());
        statement.setString(2, education.getDegree());
        statement.setString(3, education.getFieldStudy());
        statement.setString(4, education.getGrade());
       // statement.setString(5, education.getDetail());
        statement.setString(5, education.getDescriptionActivity());
        statement.setString(6, education.getDescription());
        statement.setString(7, education.getSkills());
        statement.setString(8, email);

        statement.executeUpdate();
    }

    public void editEducationDetail(Education education, String email, String school) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE educations SET nameschool = ?, degree = ?, fieldofstudy = ? , grade = ?, descriptionActivity = ?, description = ?, skill = ?, WHERE email = ? AND nameschool = ?");

        statement.setString(1, education.getNameSchool());
        statement.setString(2, education.getDegree());
        statement.setString(3, education.getFieldStudy());
        statement.setString(4, education.getGrade());
        statement.setString(5, education.getDescriptionActivity());
        statement.setString(6, education.getDescription());
        statement.setString(7, education.getSkills());
        statement.setString(8, email);
        statement.setString(9, school);

        statement.executeUpdate();
    }

    public ArrayList<Education> getEducations(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM educations WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        Education education = new Education();
        ArrayList<Education> educations = new ArrayList<>();
        while (resultSet.next()) {
            education.setDegree(resultSet.getString("degree"));
            education.setNameSchool(resultSet.getString("nameschool"));
            education.setFieldStudy(resultSet.getString("fieldofstudy"));
            education.setGrade(resultSet.getString("grade"));
            education.setDescriptionActivity(resultSet.getString("descriptionActivity"));
            education.setDescription(resultSet.getString("description"));
            education.setSkills(resultSet.getString("skill"));
            education.setEmail(email);
            educations.add(education);
        }
        return educations;
    }

    public Education getEducation(String email, String school) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM educations WHERE email = ? AND nameschool = ?");
        statement.setString(1, email);
        statement.setString(2, school);
        ResultSet resultSet = statement.executeQuery();
        Education education = new Education();
        if (resultSet.next()) {
            education.setDegree(resultSet.getString("degree"));
            education.setNameSchool(resultSet.getString("nameschool"));
            education.setFieldStudy(resultSet.getString("fieldofstudy"));
            education.setGrade(resultSet.getString("grade"));
            education.setDescriptionActivity(resultSet.getString("descriptionActivity"));
            education.setDescription(resultSet.getString("description"));
            education.setSkills(resultSet.getString(("skill")));
            education.setEmail(email);
            return education;
        } else {
            return null;
        }
    }

    public ArrayList<Education> getEducations() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM education");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Education> educations = new ArrayList<>();
        while (resultSet.next()) {
            Education education = new Education();
            education.setDegree(resultSet.getString("degree"));
            education.setNameSchool(resultSet.getString("nameschool"));
            education.setFieldStudy(resultSet.getString("fieldofstudy"));
            education.setGrade(resultSet.getString("grade"));
            education.setDescriptionActivity(resultSet.getString("descriptionActivity"));
            education.setDescription(resultSet.getString("description"));
            education.setEmail(resultSet.getString("email"));
            education.setSkills(resultSet.getString("skill"));
            educations.add(education);
        }
        return educations;
    }

    public void deleteEducation(String email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM education WHERE email = ?");
        statement.setString(1, email);
        statement.executeUpdate();
    }

    public void deleteEducation(String email, String school) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM education WHERE email = ? AND nameschool = ?");

        statement.setString(1, email);
        statement.setString(2, school);

        statement.executeUpdate();
    }


    public void deleteEducations() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE * FROM education");
        statement.execute();
    }

}


