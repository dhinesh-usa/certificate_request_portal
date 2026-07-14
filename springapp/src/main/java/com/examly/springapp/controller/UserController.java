package com.examly.springapp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.UpdateProfileRequest;
import com.examly.springapp.dto.UserResponseDto;
import com.examly.springapp.service.UserProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Endpoints for users to view and update their own profile")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getProfile(Principal principal) {
        return ResponseEntity.ok(userProfileService.getProfile(principal.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateProfile(Principal principal, @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(principal.getName(), request));
    }
}
