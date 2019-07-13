package ua.hunky.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Person {
    private Long personID;
    private String name;
    private String surname;
    private String email;
    private String dateOfBirth;
    private int userID;

    public Person() {
    }

    public Person(String name, String surname, String email, String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String name, String surname, String email, String dateOfBirth, int userID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.userID = userID;
    }

    public Person(Long personID, String name, String surname, String email, String dateOfBirth, int userID) {
        this(name, surname, email, dateOfBirth, userID);
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getPersonID() {
        return personID;
    }

    public int getUserID() {
        return userID;
    }

}
