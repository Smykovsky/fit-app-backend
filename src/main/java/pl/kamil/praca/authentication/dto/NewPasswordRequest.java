package pl.kamil.praca.authentication.dto;

import lombok.Getter;

@Getter
public class NewPasswordRequest {
    private String username;
    private String newPassword;
}
