package com.examly.springapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRequest {
    
    @NotBlank(message = "Name is Required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter the valid Email.")
    private String email;

    @NotBlank(message = "Password is Required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Phone Number is Required.")
    @Size(min = 10, max = 10, message = "Phone Number must be 10 digits.")
    private String phonenumber;
}
