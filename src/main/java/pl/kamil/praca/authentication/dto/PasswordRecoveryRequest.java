package pl.kamil.praca.authentication.dto;

import lombok.Data;

@Data
public class PasswordRecoveryRequest {
    private String username;
    private String email;
}
