package com.springboot.carsharing.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER("user"), ROLE_ADMIN("admin");

    Role(String auth) {
        this.auth = auth;
    }

    private final String auth;

    @Override
    public String getAuthority() {
        return auth;
    }
}
