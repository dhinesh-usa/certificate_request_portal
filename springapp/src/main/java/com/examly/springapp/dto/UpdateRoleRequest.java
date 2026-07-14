package com.examly.springapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UpdateRoleRequest {

    @NotNull(message = "Role is required.")
    @Pattern(regexp = "STUDENT|STAFF|ADMIN", message = "Role must be STUDENT, STAFF, or ADMIN.")
    private String role;
}
