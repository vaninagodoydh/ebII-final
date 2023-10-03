package com.example.msusers.domain.repositories;

import com.example.msusers.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KeycloakRepository implements IUserRepository {
    private final Keycloak keycloak;

    private final IBillsFeignRepository iBillsFeignRepository;
    @Value("${dh.keycloak.realm}")
    private String realm;

    public List<User> findAll(Boolean enabled) {
        return keycloak
                .realm(realm)
                .users()
                .list()
                .stream().filter(user -> enabled == null || Objects.equals(enabled, user.isEnabled()))
                            .map(this::fromRepresentation).collect(Collectors.toList());
    }
    public List<User> findByUsername(String username) {
        List<UserRepresentation> userRepresentation = keycloak
                .realm(realm)
                .users()
                .search(username);
        return userRepresentation.stream().map(this::fromRepresentation).collect(Collectors.toList());
    }
    public List<User> findByFirstName(String firstName) {
        List<UserRepresentation> userRepresentation = keycloak
                .realm(realm)
                .users()
                .search(firstName);
        return userRepresentation.stream().map(this::fromRepresentation).collect(Collectors.toList());
    }
//cambie el metodo de abajo por este:
    public Optional<User> findById(String id) {
        UserRepresentation userRepresentation;
        try {
            userRepresentation = keycloak.realm(realm).users().get(id).toRepresentation();
        } catch (NotFoundException ex) {
            return Optional.empty();
        }
        return Optional.of(fromRepresentation(userRepresentation));
    }

//    public Optional<User> findById(String id) {
//        UserRepresentation userRepresentation;
//        try {
//            userRepresentation = keycloak.realm(realm).users().get(id).toRepresentation();
//        } catch (NotFoundException ex) {
//            return Optional.empty();
//        }
//        return Optional.of(fromRepresentation(userRepresentation));
//    }

//    public UserResource getUserResource(String id) {
//        UserRepresentation userRepresentation = keycloak
//                .realm(realm)
//                .users()
//                .get(id)
//                .toRepresentation();
//        return (UserResource) fromRepresentation(userRepresentation);
//    }

    public User updateProperty(String id, String property) {
        UserResource userResource = keycloak.realm(realm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("property", List.of(property));
        user.setAttributes(attributes);
        userResource.update(user);
        return fromRepresentation(user);
    }

    public User createUser(User user) {
        RealmResource realmResource = keycloak.realm(realm);
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getFirstName());
        realmResource.users().create(userRepresentation);
        return findByUsername(user.getUsername()).get(0);
    }

    public User deleteUser(String id) {
        UserRepresentation userRepresentation;
        try {
            userRepresentation = keycloak
                    .realm(realm)
                    .users()
                    .get(id)
                    .toRepresentation();
        } catch (NotFoundException ex) {
            return null;
        }
        keycloak.realm(realm).users().delete(id);
        return fromRepresentation(userRepresentation);
    }

//    voy a cambiar el metodo de abajo por este

    private User fromRepresentation(UserRepresentation userRepresentation) {
        return new User(
                userRepresentation.getId(),userRepresentation.getUsername(), userRepresentation.getEmail(),
                userRepresentation.getFirstName(), iBillsFeignRepository.getAllBillsByUserId(userRepresentation.getId())
        );
    }


//    private User fromRepresentation(UserRepresentation userRepresentation) {
//        String property = null;
//
//        try {
//            property = userRepresentation.getAttributes().get("property").get(0);
//        } catch (Exception ex) {
//            System.err.println("ERROR MESSAGE: " + ex.getMessage());;
//        }
//
//        return new User(userRepresentation.getId(),
//                userRepresentation.getUsername(),
//                userRepresentation.getEmail(),
//                userRepresentation.getFirstName(),
//                property);
//    }
}
