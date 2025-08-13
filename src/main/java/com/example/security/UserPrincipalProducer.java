package com.example.security;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import java.util.Set;

@ApplicationScoped
public class UserPrincipalProducer {

    @Inject
    SecurityIdentity identity;

    @Produces
    @RequestScoped
    public UserPrincipal produce() {
        String username = identity != null && identity.isAnonymous() == false ? identity.getPrincipal().getName() : "anonymous";
        Set<String> roles = identity != null ? identity.getRoles() : Set.of();
        return new UserPrincipal(username, username, roles);
    }
}
