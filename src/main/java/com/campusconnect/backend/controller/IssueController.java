package com.campusconnect.backend.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.campusconnect.backend.model.Issue;
import com.campusconnect.backend.service.IssueService;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createIssue(
            @RequestPart("issue") Issue issue,
            @RequestPart(value = "image", required = false) MultipartFile image,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Object userIdObj = request.getAttribute("userId");

        if (role == null || !"STUDENT".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403)
                    .body("Only students can create issues");
        }

        if (userIdObj == null) {
            return ResponseEntity.status(401)
                    .body("User not authenticated");
        }

        Long userId = Long.valueOf(userIdObj.toString());


        Issue savedIssue = issueService.createIssue(issue, image, userId);

        return ResponseEntity.ok(savedIssue);
    }

    // ===============================
    // STUDENT: VIEW MY ISSUES
    // ===============================
    @GetMapping("/my")
    public ResponseEntity<?> myIssues(HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        Object userIdObj = request.getAttribute("userId");

        if (role == null || !"STUDENT".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403)
                    .body("Only students can view their issues");
        }

        if (userIdObj == null) {
            return ResponseEntity.status(401)
                    .body("User not authenticated");
        }

        Long userId = Long.valueOf(userIdObj.toString());
        List<Issue> issues = issueService.getMyIssues(userId);

        return ResponseEntity.ok(issues);
    }
}
