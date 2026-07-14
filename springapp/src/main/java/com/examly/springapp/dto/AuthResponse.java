package com.examly.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    
    private Long id;

    private String name;

    private String email;
    
    private String accessToken;

    private String role;

    // private String refreshToken;

    private String message;

    @Builder.Default
    private String tokenType = "Bearer";
    
}
