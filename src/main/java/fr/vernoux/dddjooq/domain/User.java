package fr.vernoux.dddjooq.domain;

import java.util.UUID;

public class User {

    private UUID id;
    private String email;

    public User(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
