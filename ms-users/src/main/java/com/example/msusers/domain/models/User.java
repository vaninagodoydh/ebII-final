package com.example.msusers.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private List<Bill> bills;

    public User(String userId, String username, String email, String firstName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
    }
}
