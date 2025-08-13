package com.example.security;

import java.util.Set;

public class UserPrincipal {
    private final String userId;
    private final String username;
    private final Set<String> roles;

    public UserPrincipal(String userId, String username, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
