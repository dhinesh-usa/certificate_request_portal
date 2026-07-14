package com.examly.springapp.exception;

// Renamed to avoid clashing with org.springframework.security.core.userdetails.UsernameNotFoundException
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("This account is not registered. Register first: " + email);
    }
}