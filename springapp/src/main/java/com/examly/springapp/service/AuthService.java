package com.examly.springapp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.AuthResponse;
import com.examly.springapp.dto.LoginRequestDto;
import com.examly.springapp.dto.RegisterRequest;
import com.examly.springapp.exception.EmailAlreadyExistsException;
import com.examly.springapp.model.Role;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.security.JwtUtill;
import com.examly.springapp.security.UserPrinciple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtill jwtUtill;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request)
    {

        if(userRepo.existsByEmail(request.getEmail()))
        {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.STUDENT)
                    .phonenumber(request.getPhonenumber())
                    .enabled(true)
                    .build();

        User saveduser = userRepo.save(user);

        UserPrinciple userPrinciple = UserPrinciple.create(saveduser);

        String accesstoken = jwtUtill.generateAccessToken(userPrinciple);

        return AuthResponse.builder()
                .id(saveduser.getId())
                .name(saveduser.getName())
                .email(saveduser.getEmail())
                .accessToken(accesstoken)
                .role(saveduser.getRole().name())
                .message("Registration Successfull.")
                .tokenType("Bearer")
                .build();
            }
            
    @Transactional
    public AuthResponse login(LoginRequestDto request)
    {
                
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
                
        UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
                
        String accessToken = jwtUtill.generateAccessToken(userPrinciple);
                
        User saveduser = userRepo.findByEmail(userPrinciple.getEmail())
                    .orElseThrow(() -> new IllegalStateException("Authenticated user not found in database"));
                
        return AuthResponse.builder()
                            .id(saveduser.getId())
                            .name(saveduser.getName())
                            .email(saveduser.getEmail())
                            .accessToken(accessToken)
                            .role(saveduser.getRole().name())
                            .message("Login successfull.")
                            .tokenType("Bearer")
                            .build();
    }
  
}
