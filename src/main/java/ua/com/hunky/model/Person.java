package ua.com.hunky.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;

@Component
@Getter
@Setter
@Scope("prototype")
@Entity
@Table(name = "persons")
public class Person {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long personID;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "userID")
    private Long userID;

    public Person() {
    }

    public Person(String name, String surname, String email, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String name, String surname, String email, Date dateOfBirth, Long userID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.userID = userID;
    }

    public Person(Long personID, String name, String surname, String email, Date dateOfBirth, Long userID) {
        this(name, surname, email, dateOfBirth, userID);
        this.personID = personID;
    }

}
