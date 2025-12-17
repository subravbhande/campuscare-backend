package com.campusconnect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.backend.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByCreatedById(Long userId);
}
