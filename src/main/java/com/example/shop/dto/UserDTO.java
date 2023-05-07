package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object representing a user, used for authentication purpose.
 * Contains a JWT token generated for user authentication and user information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String email;
    private String username;
    private String password;
    private String id;
}
