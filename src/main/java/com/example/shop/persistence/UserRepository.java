package com.example.shop.persistence;

import com.example.shop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for performing database operations on UserEntity objects.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    /**
     * Finds a UserEntity by email.
     *
     * @param email the email to search for
     * @return the UserEntity if found, otherwise null
     */
    UserEntity findByEmail(String email);

    /**
     * Checks if a UserEntity with the given email exists.
     *
     * @param email the email to check for
     * @return true if the UserEntity exists, otherwise false
     */
    Boolean existsByEmail(String email);

    /**
     * Finds a UserEntity by email and password.
     *
     * @param email the email to search for
     * @param password the password to match
     * @return the UserEntity if found and the password matches, otherwise null
     */
    UserEntity findByEmailAndPassword(String email, String password);
}