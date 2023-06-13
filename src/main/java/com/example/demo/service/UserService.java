package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer for managing UserEntity objects (adding new user and retrieving user by id or email and password.
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Add a user to the database if they don't already exist and return the UserEntity object
    public UserEntity addUser(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String email = userEntity.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email {} already exists", email);
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Return UserEntity object if email and password are valid and exists in the database,
    // otherwise return null
    public UserEntity getUserByCredentials(final String email, final String password,
                                           final PasswordEncoder encoder) {
        final UserEntity user = userRepository.findByEmail(email);

        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
