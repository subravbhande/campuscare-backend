package com.campusconnect.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/")
    public String home() {
        return "Backend is running perfectly!";
    }


    @GetMapping("/test")
    public String test(HttpServletRequest request) {

        Object userId = request.getAttribute("userId");
        Object role = request.getAttribute("role");

        if (userId == null || role == null) {
            return "No JWT found or token invalid";
        }

        return "JWT verified successfully | userId = " + userId + " | role = " + role;
    }
}
