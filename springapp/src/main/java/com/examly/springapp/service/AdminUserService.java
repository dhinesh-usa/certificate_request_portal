package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.dto.UserResponseDto;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.Role;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepo userRepo;

    public List<UserResponseDto> getAllUsers() {
        return userRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUserRole(Long id, String role) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setRole(Role.valueOf(role));
        return toDto(userRepo.save(user));
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
