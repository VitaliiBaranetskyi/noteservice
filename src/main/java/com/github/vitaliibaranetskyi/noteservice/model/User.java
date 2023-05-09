package com.github.vitaliibaranetskyi.noteservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;

    private String password;

    private Role role;

    public User(Role role) {
        this.role = role;
    }
}
