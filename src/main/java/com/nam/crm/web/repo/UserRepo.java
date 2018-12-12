package com.nam.crm.web.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nam.crm.model.jpa.User;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findOneByUserId(String userId);
    Optional<User> findOneByUserIdAndPassword(String userId, String password);
}

