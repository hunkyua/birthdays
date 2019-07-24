package ua.com.hunky.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Scope("prototype")
public class User {
    private int userID;
    private String login = "";
    private String password = "";
    private ROLE role = ROLE.UNKNOWN;

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

}





