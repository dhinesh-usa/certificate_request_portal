package com.examly.springapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String name;

    @Email(message = "Enter a valid email.")
    private String email;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits.")
    private String phonenumber;
}
