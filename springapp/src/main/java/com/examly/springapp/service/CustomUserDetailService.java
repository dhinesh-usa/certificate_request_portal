package com.examly.springapp.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.security.UserPrinciple;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor

public class CustomUserDetailService implements UserDetailsService{

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email){
        
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return UserPrinciple.create(user);
    }
    
}
