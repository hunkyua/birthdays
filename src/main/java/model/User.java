package model;

import static model.ROLE.UNKNOWN;

public class User {
    private int userID;
    private String login = "";
    private String password = "";
    private ROLE role = UNKNOWN;

    public User() {

    }

    public User(String login, String password, ROLE role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ROLE getRole() {
        return role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public boolean isUserExist(User user) {
        if ((user.getLogin() == null || user.getLogin().isEmpty()) &&
                (user.getPassword() == null || user.getPassword().isEmpty())) {
            return false;
        }

        return user.getLogin().equals(login) && user.getPassword().equals(password);
    }

    public ROLE getUserRole() {
        if (role != null) {
            return role;
        }
        return ROLE.UNKNOWN;
    }

}





