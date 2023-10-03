package com.example.msusers.application.rest.controller;

import com.example.msusers.domain.models.User;
import com.example.msusers.infrastructure.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all/{enabled}")
    public ResponseEntity<List<User>> findAllUsers(@PathVariable Boolean enabled) {
        return ResponseEntity.ok(userService.findAll(enabled));
    }

    @GetMapping("/billsPerUser/{userId}")
    public ResponseEntity<User> findAllUsers(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findAllBillsPerUser(userId));
    }
    //este endpoint solo permite a los usuarios chequear sus propias facturas
    @GetMapping("/billsPerUser/vanina/{userId}")
    public ResponseEntity<User> findAllUsersVanina(@PathVariable String userId,@AuthenticationPrincipal Jwt source) {
        String id = (String) source.getClaims().get("sub");
        if (userId.equalsIgnoreCase(id)) {
            return ResponseEntity.ok(userService.findAllBillsPerUser(userId));
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/username/{username}")
    public List<User> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }


    @GetMapping("/id/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.findById(id);
    }

}
