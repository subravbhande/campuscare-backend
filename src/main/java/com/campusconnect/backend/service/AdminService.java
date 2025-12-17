package com.campusconnect.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.campusconnect.backend.model.Issue;
import com.campusconnect.backend.repository.IssueRepository;

@Service
public class AdminService {

    private final IssueRepository issueRepository;

    public AdminService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public void updateIssueStatus(Long id, String status) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        issue.setStatus(status);
        issueRepository.save(issue);
    }
}
