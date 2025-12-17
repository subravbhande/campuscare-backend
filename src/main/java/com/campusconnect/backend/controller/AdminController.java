package com.campusconnect.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.backend.model.Issue;
import com.campusconnect.backend.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/issues")
    public ResponseEntity<List<Issue>> getAllIssues() {
        return ResponseEntity.ok(adminService.getAllIssues());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/issues/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        adminService.updateIssueStatus(id, status);
        return ResponseEntity.ok("Status updated");
    }
}
