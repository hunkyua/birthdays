package ua.hunky.model;

public enum ROLE {
    UNKNOWN, ADMIN, USER;

    public static ROLE getRoleByNumber(int num) {
        switch (num) {
            case 1 : return ADMIN;
            case 2 : return USER;
            default: return UNKNOWN;
        }
    }

}