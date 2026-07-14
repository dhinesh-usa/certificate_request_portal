package com.examly.springapp.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequestDto {
    
    @NotBlank(message = "Course Name is Required.")
    private String coursename;

    @NotBlank(message = "Course Id is Required.")
    private String courseid;

    @NotBlank(message = "Email is Required.")
    private String email;

    @NotNull(message = "Completion Date is Required.")
    @PastOrPresent(message = "Completion Date must be today or past date.")
    private LocalDate completionDate;

    private String specialInstruction;
}
