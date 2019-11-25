package ua.com.hunky.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    UNKNOWN, ADMIN, USER;


    @Override
    public String getAuthority() {
        return name();
    }
}