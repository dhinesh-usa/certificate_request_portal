package com.examly.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequestDto {

    @NotBlank(message = "The Email is Required.")
    @Email(message = "The Email must be valid.")
    private String email;

    @NotBlank(message = "Password is Requried.")
    private String password;
    
}
