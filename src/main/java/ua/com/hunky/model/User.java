package ua.com.hunky.model;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Component
@Getter
@Setter
@Scope("prototype")
@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userID;
    @Column(name = "login")
    private String login = "";
    @Column(name = "password")
    private String password = "";
    @Column(name = "role")
    private ROLE role = ROLE.UNKNOWN;

//    List<Person> person;

    public User() {
    }

    public User(String login, String password, ROLE role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public boolean isUserExist() {
        if ((this.getLogin() == null || this.getLogin().isEmpty()) &&
                (this.getPassword() == null || this.getPassword().isEmpty())) {
            return false;
        }

        return this.getLogin().equals(login) && this.getPassword().equals(password);
    }

    public ROLE getUserRole() {
        if (role != null) {
            return role;
        }
        return ROLE.UNKNOWN;
    }

//    public List<Person> getPersons() {
//        return null;
//    }
}





