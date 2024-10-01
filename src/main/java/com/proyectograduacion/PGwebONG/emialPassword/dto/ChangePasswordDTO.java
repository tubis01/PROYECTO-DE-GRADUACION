package com.proyectograduacion.PGwebONG.emialPassword.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangePasswordDTO {
    @NotBlank
    private String tokenPassword;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;


}
