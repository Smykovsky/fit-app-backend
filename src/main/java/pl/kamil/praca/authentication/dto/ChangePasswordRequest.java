package pl.kamil.praca.authentication.dto;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String username;
    private String newPassword;
    private String newPasswordConfirmed;
}
