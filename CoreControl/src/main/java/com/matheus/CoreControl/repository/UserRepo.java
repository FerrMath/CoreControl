package com.matheus.CoreControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheus.CoreControl.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    // Custom queries are created here
    User findByLogin(String login);
}
