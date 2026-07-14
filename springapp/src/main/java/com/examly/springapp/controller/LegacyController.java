package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.CertificateRequest;
import com.examly.springapp.repository.CertificateRequestRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LegacyController {

    private final CertificateRequestRepository certificateRequestRepository;

    @PostMapping("/addRequest")
    public ResponseEntity<CertificateRequest> addRequest(@RequestBody CertificateRequest request) {
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/getAllRequests")
    public ResponseEntity<List<CertificateRequest>> getAllRequests() {
        return ResponseEntity.ok(certificateRequestRepository.findAll());
    }
}
