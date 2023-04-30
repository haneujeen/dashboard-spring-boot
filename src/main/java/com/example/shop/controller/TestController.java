package com.example.shop.controller;

import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// This class is a REST controller that handles requests for the "/api/test" endpoint
@RestController
@RequestMapping("/api/test")
public class TestController {

    // A GET request that returns a simple message
    @GetMapping("/message")
    public String getMessage() {
        return "This is a test message.";
    }

    // A GET request that returns a message with a path variable
    @GetMapping("/{id}")
    public String getMessageWithPathVariable(@PathVariable(required = false) String id) {
        return "This is a test message with path variable '" + id + "'.";
    }

    // A GET request that returns a message with a request parameter
    @GetMapping("/param")
    public String getMessageWithRequestParam(@RequestParam(required = false) String message) {
        return "This is a test message with request parameter: " + message;
    }

    // A GET request that returns a message with a request body parameter
    @GetMapping("/body")
    public String getMessageWithRequestBody(@RequestBody TestRequestBodyDTO requestBody) {
        return "This is a test message with id '" + requestBody.getId() + "' and message " + requestBody.getMessage();
    }

    // A GET request that returns a response DTO containing a list of strings
    @GetMapping("/response")
    public ResponseDTO<String> getMessageWithResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("This is the first string in the list");
        list.add("This is the second string in the list");

        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }

    // A GET request that returns a response entity with a status containing a response DTO
    @GetMapping("/response-entity")
    public ResponseEntity<?> getMessageWithResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("This is the first string in the list");
        list.add("This is the second string in the list");

        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }
}
