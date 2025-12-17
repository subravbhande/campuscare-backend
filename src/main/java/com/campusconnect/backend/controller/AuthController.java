package com.campusconnect.backend.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.backend.model.User;
import com.campusconnect.backend.security.JwtUtil;
import com.campusconnect.backend.service.UserService;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER STUDENT
    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody User user) {
        userService.registerStudent(user);
        return ResponseEntity.ok("Student registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {

        Optional<User> userOpt =
                userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            String token = jwtUtil.generateToken(
                    user.getId(),
                    user.getEmail(),
                    user.getRole()
            );

            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid email or password");
    }
}
