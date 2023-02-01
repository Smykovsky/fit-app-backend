package pl.kamil.praca.authentication.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter @Setter
public class LoginRequest {
    @NotBlank(message = "Podaj nazwę użytkownika!")
    private String username;

    @NotBlank(message = "Podaj hasło!")
    private String password;
}
