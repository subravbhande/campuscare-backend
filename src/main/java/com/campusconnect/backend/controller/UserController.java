package com.campusconnect.backend.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {

        Object userIdObj = request.getAttribute("userId");

        if (userIdObj == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Long userId = Long.valueOf(userIdObj.toString());
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        boolean success = userService.changePassword(userId, oldPassword, newPassword);

        if (!success) {
            return ResponseEntity.badRequest().body("Old password incorrect");
        }

        return ResponseEntity.ok("Password updated");
    }
}
