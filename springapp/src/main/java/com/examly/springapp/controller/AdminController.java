package com.examly.springapp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.UpdateRoleRequest;
import com.examly.springapp.dto.UserResponseDto;
import com.examly.springapp.service.AdminUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - User Management", description = "Admin endpoints to view users and update roles")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponseDto> updateUserRole(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(adminUserService.updateUserRole(id, request.getRole()));
    }
}
