package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data transfer object for representing a response.
 * The generic type T represents the type of data contained in the response.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {

    // The data to be returned in the response
    private List<T> data;

    // Error message to be included in the response in case of an error
    private String errorMessage;

    // Constructor for response with data
    public ResponseDTO(List<T> data) {
        this.data = data;
    }

    // Constructor for response with optional error message
    public ResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}