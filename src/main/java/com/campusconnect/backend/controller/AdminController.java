package com.campusconnect.backend.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.backend.model.Issue;
import com.campusconnect.backend.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*") // works for Vercel + Render
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ===============================
    // ADMIN: VIEW ALL ISSUES
    // ===============================
    @GetMapping("/issues")
    public ResponseEntity<?> getAllIssues(HttpServletRequest request) {

        String role = (String) request.getAttribute("role");

        if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403)
                    .body("Only admin allowed");
        }

        return ResponseEntity.ok(adminService.getAllIssues());
    }

    // ===============================
    // ADMIN: UPDATE ISSUE STATUS
    // ===============================
    @PutMapping("/issues/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");

        if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403)
                    .body("Only admin allowed");
        }

        adminService.updateIssueStatus(id, status);
        return ResponseEntity.ok("Status updated");
    }
}
