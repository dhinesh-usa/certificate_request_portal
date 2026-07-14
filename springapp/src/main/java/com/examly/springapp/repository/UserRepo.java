package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.User;

public interface UserRepo extends JpaRepository<User , Long>{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    
}
