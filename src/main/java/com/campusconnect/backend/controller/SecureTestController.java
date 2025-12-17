package com.campusconnect.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SecureTestController {

    @GetMapping("/secure/test")
    public String secure(HttpServletRequest request) {
        return "User ID: " + request.getAttribute("userId")
                + " Role: " + request.getAttribute("role");
    }
}

