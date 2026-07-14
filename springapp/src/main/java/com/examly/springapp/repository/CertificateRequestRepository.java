package com.examly.springapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.CertificateRequest;

public interface CertificateRequestRepository extends JpaRepository<CertificateRequest , Long>{

    Page<CertificateRequest> findByStudentId(Long studentId, Pageable pageable);
    
}
