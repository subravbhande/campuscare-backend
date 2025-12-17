package com.campusconnect.backend.service;

import com.campusconnect.backend.model.User;
import com.campusconnect.backend.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // ================= REGISTER STUDENT =================
    public User registerStudent(User user) {
        user.setRole("STUDENT");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // ================= REGISTER ADMIN =================
    // (Only for initial setup / testing)
    public User registerAdmin(User user) {
        user.setRole("ADMIN");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // ================= LOGIN =================
    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // ================= CHANGE PASSWORD =================
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        // verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // update new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }
}
