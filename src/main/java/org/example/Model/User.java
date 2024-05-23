package org.example.Model;

<<<<<<< HEAD
public class User {
    private Contacts contacts ;
    private Education education;
    private String userId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String profession;

    public User(Contacts contacts, Education education,String userId, String firstName, String lastName, String country, String city, String profession) {
        this.contacts = contacts;
        this.education = education;
        this.userId=userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.profession = profession;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
=======
import java.util.Date;

public class User {

  private String id;
  private String name;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String password;
  private String country;
  private Date birthday;
  private Date created_at;

  public User(String id, String name, String lastName, String email, String phoneNumber,
      String password, String country, Date birthday) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.country = country;
    this.birthday = birthday;
    this.created_at = new Date(System.currentTimeMillis());
  }

  public Date getCreated_at() {
    return created_at;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
>>>>>>> origin/reza
}
