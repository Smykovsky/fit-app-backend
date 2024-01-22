package pl.kamil.praca.authentication.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Podaj nazwę użytkownika!")
    private String username;
    @NotBlank(message = "Podaj hasło!")
    private String password;
}


