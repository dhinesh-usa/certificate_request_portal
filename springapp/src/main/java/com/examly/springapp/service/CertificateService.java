package com.examly.springapp.service;


import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.AdminCertificateResponseDto;
import com.examly.springapp.dto.CertificateRequestDto;
import com.examly.springapp.dto.CertificateResponseDto;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.CertificateRequest;
import com.examly.springapp.model.Status;
import com.examly.springapp.repository.CertificateRequestRepository;
import com.examly.springapp.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRequestRepository certificateRequestRepository;
    private final CertificateGeneratorService certificateGeneratorService;
    private final CurrentUserProvider currentUserProvider;
    
    public CertificateResponseDto SubmitRequest(CertificateRequestDto request)
    {
        CertificateRequest saved = CertificateRequest.builder()
                                .student(currentUserProvider.getCurrentUser())
                                .name(request.getCoursename())
                                .course(request.getCourseid())
                                .email(request.getEmail())
                                .special_instruction(request.getSpecialInstruction())
                                .completionDate(request.getCompletionDate())
                                .status(Status.PENDING)
                                .build();

        certificateRequestRepository.save(saved);

        return CertificateResponseDto.builder()
                .requestId(saved.getId())
                .certificateUrl(saved.getUrl())
                .status(saved.getStatus().name())
                .courseName(saved.getName())
                .message("Request Submitted Successfully")
                .build();
        
    }

    public Page<CertificateResponseDto> getRequestByUserId(Long userId, Pageable pageable)
    {
        return certificateRequestRepository.findByStudentId(userId, pageable)
                .map(request -> {

                    CertificateResponseDto dto = new CertificateResponseDto();

                    dto.setRequestId(request.getId());
                    dto.setStatus(request.getStatus().name());
                    dto.setCourseName(request.getName());
                    dto.setCertificateUrl(request.getUrl());
                    dto.setMessage("Data retrieved successfully");
                return dto;
    });
    }

    public Page<AdminCertificateResponseDto> getAllRequests(Pageable pageable) {
        return certificateRequestRepository.findAll(pageable)
                .map(request -> AdminCertificateResponseDto.builder()
                        .requestId(request.getId())
                        .studentName(request.getStudent() != null ? request.getStudent().getName() : request.getEmail())
                        .studentEmail(request.getStudent() != null ? request.getStudent().getEmail() : request.getEmail())
                        .courseName(request.getName())
                        .courseId(request.getCourse())
                        .completionDate(request.getCompletionDate())
                        .specialInstruction(request.getSpecial_instruction())
                        .status(request.getStatus().name())
                        .certificateUrl(request.getUrl())
                        .build());
    }

    public AdminCertificateResponseDto approveRequest(Long requestId) {
        CertificateRequest request = certificateRequestRepository.findById(requestId)
                .orElseThrow(() -> new UserNotFoundException("Request not found with id: " + requestId));
        request.setStatus(Status.APPROVED);

        try {
            String studentName = request.getStudent() != null ? request.getStudent().getName() : request.getEmail();
            String url = certificateGeneratorService.generate(
                    request.getId(), studentName, request.getName(), request.getCompletionDate());
            request.setUrl(url);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate certificate for request: " + requestId, e);
        }

        certificateRequestRepository.save(request);

        String studentName = request.getStudent() != null ? request.getStudent().getName() : request.getEmail();
        String studentEmail = request.getStudent() != null ? request.getStudent().getEmail() : request.getEmail();

        return AdminCertificateResponseDto.builder()
                .requestId(request.getId())
                .studentName(studentName)
                .studentEmail(studentEmail)
                .courseName(request.getName())
                .certificateUrl(request.getUrl())
                .status(request.getStatus().name())
                .build();
    }

    public AdminCertificateResponseDto rejectRequest(Long requestId) {
        CertificateRequest request = certificateRequestRepository.findById(requestId)
                .orElseThrow(() -> new UserNotFoundException("Request not found with id: " + requestId));
        request.setStatus(Status.REJECTED);
        certificateRequestRepository.save(request);

        String studentName = request.getStudent() != null ? request.getStudent().getName() : request.getEmail();
        String studentEmail = request.getStudent() != null ? request.getStudent().getEmail() : request.getEmail();

        return AdminCertificateResponseDto.builder()
                .requestId(request.getId())
                .studentName(studentName)
                .studentEmail(studentEmail)
                .courseName(request.getName())
                .status(request.getStatus().name())
                .build();
    }
}
