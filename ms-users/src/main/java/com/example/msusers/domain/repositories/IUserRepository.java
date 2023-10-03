package com.example.msusers.domain.repositories;

import com.example.msusers.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    List<User> findAll(Boolean enabled);
    List<User> findByUsername(String username);
    List<User> findByFirstName(String firstName);
    Optional<User>
    findById(String id);
    User updateProperty(String id, String nationality);
    User createUser(User user);
    User deleteUser(String id);
}
