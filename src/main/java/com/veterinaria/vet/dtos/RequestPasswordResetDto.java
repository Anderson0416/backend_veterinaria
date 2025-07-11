package com.veterinaria.vet.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestPasswordResetDto {
    @NotBlank
    @Email
    private String email;
}
