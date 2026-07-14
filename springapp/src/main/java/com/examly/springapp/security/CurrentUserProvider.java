package com.examly.springapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final UserRepo userRepo;
    
    public User getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();

        User user = userRepo.findByEmail(name)
                            .orElseThrow(() -> new RuntimeException("user not found"));
        
        return user;
        
    }
}
