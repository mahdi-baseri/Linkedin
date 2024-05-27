package com.example.linkedin.DataAccess;

import com.example.linkedin.Model.Contacts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDAO {
    private Connection connection ;

    public ContactsDAO(Connection connection) throws SQLException {
        this.connection = DatabaseConnector.getConnection();
        createContactsTable();
    }
    public void createContactsTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXSIST contacts(userId varchar() , email varchar() , phonenumber varchar(),address varchar(),dateofbirth varchar())");
        statement.executeUpdate();
    }
    public void addContacts(Contacts contacts) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO educations (userId, email,phonenumber, address , dateofbirth) VALUES ( ?, ? , ? , ? , ?)");
        statement.setString(1, contacts.getUserId());
        statement.setString(2, contacts.getEmail());
        statement.setString(3, contacts.getPhoneNumber());
        statement.setString(4, contacts.getAddress());
        statement.setString(5, contacts.getDateOfBirth().toString());

        statement.executeUpdate();
    }
    public void deleteContact(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE useId = ?");
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    public void deleteAllContact() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts");
        statement.executeUpdate();
    }
    public void updateContacts(Contacts contacts) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE contacts SET email = ?, phonenumber = ?,address = ? , dateofbirth= ? ,WHERE user_id = ?");
        statement.setString(1, contacts.getEmail());
        statement.setString(2, contacts.getPhoneNumber());
        statement.setString(3, contacts.getAddress());
        statement.setString(4, contacts.getDateOfBirth().toString());

        statement.executeUpdate();
    }
    public Contacts getContact(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts WHERE user_id = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Contacts contacts = new Contacts();
            contacts.setUserId(resultSet.getString("userId"));
            contacts.setEmail(resultSet.getString("email"));
            contacts.setPhoneNumber(resultSet.getString("phonenumber"));
            contacts.setAddress(resultSet.getString("address"));
            contacts.setUserId(resultSet.getString("dateofbirth"));

            return contacts;
        }

        return null;
    }
}
