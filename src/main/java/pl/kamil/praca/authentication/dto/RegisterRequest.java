package pl.kamil.praca.authentication.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RegisterRequest {
    @NotBlank(message = "Wprowadź swój email!")
    private String email;
    @NotBlank(message = "Podaj nazwę użytkownika!")
    private String username;
    @NotBlank(message = "Wprowadź hasło!")
    private String password;
    @NotBlank(message = "Potwierdź hasło!")
    private String password_confirmed;
}
