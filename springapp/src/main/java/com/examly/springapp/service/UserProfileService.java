package com.examly.springapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.UpdateProfileRequest;
import com.examly.springapp.dto.UserResponseDto;
import com.examly.springapp.exception.EmailAlreadyExistsException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepo userRepo;

    public UserResponseDto getProfile(String email) {
        return toDto(findByEmail(email));
    }

    @Transactional
    public UserResponseDto updateProfile(String email, UpdateProfileRequest request) {
        User user = findByEmail(email);

        if (request.getEmail() != null && !request.getEmail().equals(email)) {
            if (userRepo.existsByEmail(request.getEmail()))
                throw new EmailAlreadyExistsException(request.getEmail());
            user.setEmail(request.getEmail());
        }
        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhonenumber() != null) user.setPhonenumber(request.getPhonenumber());

        return toDto(userRepo.save(user));
    }

    private User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .phonenumber(user.getPhonenumber())
                .enabled(user.isEnabled())
                .build();
    }
}
