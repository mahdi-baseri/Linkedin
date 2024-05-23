package org.example.DataAccess;

import org.example.Model.Education;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EducationDAO {
    private Connection connection ;

    public EducationDAO(Connection connection) throws SQLException {
        this.connection = DatabaseConnector.getConnection();
        createEducationTable();
    }
    public void createEducationTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXSIST educations(userId varchar() , nameschool varchar() , fieldstudy varchar(), startdate varchar(),enddate varchar(),description varchar(),skills varchar())");
        statement.executeUpdate();
    }
    public void addEducation(Education education) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO educations (userId, nameschool,fieldstudy, startdate , enddate , description , skills ) VALUES (?, ? , ?, ? , ? , ? , ?)");
        statement.setString(1, education.getUserId());
        statement.setString(2, education.getNameSchool());
        statement.setString(3, education.getFieldStudy());
        statement.setString(4, education.getDateStart().toString());
        statement.setString(5, education.getDateEnd().toString());
        statement.setString(6, education.getDescription());
        statement.setString(7, education.getSkills());
        statement.executeUpdate();
    }
    public void deleteEducation(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM educations WHERE user_id = ?");
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    public void deleteEducations() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM educations");
        statement.executeUpdate();
    }
    public void updateEducations(Education education) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE bios SET nameschool = ?, fieldstudy = ?, startdate = ? , enddate = ? , descriptions = ? , skills = ? ,WHERE user_id = ?");
        statement.setString(1, education.getNameSchool());
        statement.setString(2, education.getFieldStudy());
        statement.setString(3, education.getDateStart().toString());
        statement.setString(4, education.getDateEnd().toString());
        statement.setString(5, education.getDescription());
        statement.setString(6, education.getSkills());

        statement.executeUpdate();
    }
    public Education getEducation(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM bios WHERE user_id = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Education education = new Education();
            education.setUserId(resultSet.getString("userId"));
            education.setNameSchool(resultSet.getString("nameschool"));
            education.setFieldStudy(resultSet.getString("fieldstudy"));
            education.setDateStart(resultSet.getDate("startdate"));
            education.setDateEnd(resultSet.getDate("enddate"));
            education.setDescription(resultSet.getString("description"));
            education.setSkills(resultSet.getString("skills"));
            return education;
        }

        return null;
    }
}
