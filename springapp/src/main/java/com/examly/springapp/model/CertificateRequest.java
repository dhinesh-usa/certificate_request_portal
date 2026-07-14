package com.examly.springapp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.FetchType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "certificate_requests")
public class CertificateRequest {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY , optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User student;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate completionDate;

    //course id
    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String url;

    private String special_instruction;
    
}
