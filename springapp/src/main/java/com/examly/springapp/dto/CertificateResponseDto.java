package com.examly.springapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponseDto {
    
    private long requestId;

    private String certificateUrl;

    private String status;

    private String courseName;

    private String message;

}
