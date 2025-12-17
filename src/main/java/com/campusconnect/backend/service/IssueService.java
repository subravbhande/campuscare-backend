package com.campusconnect.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.campusconnect.backend.model.Issue;
import com.campusconnect.backend.model.User;
import com.campusconnect.backend.repository.IssueRepository;
import com.campusconnect.backend.repository.UserRepository;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueService(IssueRepository issueRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    // ===============================
    // CREATE ISSUE (STUDENT)
    // ===============================
    public Issue createIssue(Issue issue, MultipartFile image, Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        issue.setCreatedBy(user);
        issue.setStatus("OPEN");

        // OPTIONAL IMAGE
        if (image != null && !image.isEmpty()) {
            issue.setImagePath(image.getOriginalFilename());
        }

        return issueRepository.save(issue);
    }

    // ===============================
    // GET MY ISSUES
    // ===============================
    public List<Issue> getMyIssues(Long userId) {
        return issueRepository.findByCreatedById(userId);
    }
}
