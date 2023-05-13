package com.example.shop.controller;

import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.model.UserEntity;
import com.example.shop.security.TokenProvider;
import com.example.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController handles requests related to User resource.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Creates a new user
     * @param userDTO UserDTO containing user details
     * @return ResponseEntity containing the created user's data or an error response
     */
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            // Map UserDTO to UserEntity and add the user to the database
            UserEntity userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();

            UserEntity createdUser = userService.addUser(userEntity);

            // Return the created user's data
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(createdUser.getEmail())
                    .id(createdUser.getId())
                    .username(createdUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            ResponseDTO responseDTO = ResponseDTO.builder().errorMessage(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    /**
     * Authenticates a user
     * @param userDTO UserDTO containing user credentials
     * @return ResponseEntity containing the authenticated user's data or an error response
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        // Check if the user credentials are valid
        UserEntity user = userService.getUserByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword(),
                passwordEncoder
        );

        if (user != null) {
            final String token = tokenProvider.generate(user);

            // If the user credentials are valid, return the user's data
            final UserDTO responseUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            // If the user credentials are not valid, return an error response
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .errorMessage("Invalid email or password")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
