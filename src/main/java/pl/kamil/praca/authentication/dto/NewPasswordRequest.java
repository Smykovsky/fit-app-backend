package pl.kamil.praca.authentication.dto;

import lombok.Getter;

@Getter
public class NewPasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmed;
}
