package ua.com.hunky.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
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

}
