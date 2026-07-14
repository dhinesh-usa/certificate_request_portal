package com.examly.springapp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CertificateRequestDto;
import com.examly.springapp.dto.CertificateResponseDto;
import com.examly.springapp.model.User;
import com.examly.springapp.security.CurrentUserProvider;
import com.examly.springapp.service.CertificateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@Tag(name = "Student Requests", description = "Student endpoints to submit and view certificate requests")
@SecurityRequirement(name = "bearerAuth")
public class ApiController {

    private final CertificateService certificateService;
    private final CurrentUserProvider currentUserProvider;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<CertificateResponseDto> submitRequest(@Valid @RequestBody CertificateRequestDto request)
    {
        return ResponseEntity.status(201).body(certificateService.SubmitRequest(request));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/dashboard")
    public ResponseEntity<Page<CertificateResponseDto>> getRequestByid(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "Desc") String sortDir)
    {
        User currentuser = currentUserProvider.getCurrentUser();

        Sort sort = sortDir.equalsIgnoreCase("asc")
                    ?Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(certificateService.getRequestByUserId(currentuser.getId(),pageable));
    }

    
}
