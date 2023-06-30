package pl.kamil.praca.authentication.dto;

import lombok.Getter;

@Getter
public class PasswordRecoveryRequest {
    private String username;
    private String email;
}
