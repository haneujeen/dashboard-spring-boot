package com.example.shop.service;

import com.example.shop.model.UserEntity;
import com.example.shop.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer for managing UserEntity objects.
 * Contains methods for adding new users and retrieving existing users by credentials.
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // This method adds a user to the database if they don't already exist
    // and returns the saved user object
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

    // This method returns a user by their email and password if the combination
    // is valid and exists in the database, otherwise it returns null
    public UserEntity getUserByCredentials(final String email, final String password,
                                           final PasswordEncoder encoder) {
        final UserEntity user = userRepository.findByEmail(email);

        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
