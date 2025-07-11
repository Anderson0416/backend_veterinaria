package com.veterinaria.vet.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto {

    @NotBlank
    private String login;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
