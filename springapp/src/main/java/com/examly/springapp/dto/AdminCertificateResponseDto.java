package com.examly.springapp.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCertificateResponseDto {

    private Long requestId;
    private String studentName;
    private String studentEmail;
    private String courseName;
    private String courseId;
    private LocalDate completionDate;
    private String specialInstruction;
    private String status;
    private String certificateUrl;
}
