package pl.kamil.praca.authentication.dto;

import lombok.Data;

@Data
public class NewPasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmed;
}
