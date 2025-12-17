package com.campusconnect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.campusconnect.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
