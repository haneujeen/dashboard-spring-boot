package com.example.shop.controller;

import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/message/{id}")
    public String getMessageWithPathVariable(@PathVariable(required = false) String id) {
        return "This is a test message with path variable '" + id + "'.";
    }

    // A GET request that returns a message with a request parameter
    @GetMapping("/message")
    public String getMessageWithRequestParam(@RequestParam(required = false) String message) {
        return "This is a test message with request parameter: " + message;
    }
}
