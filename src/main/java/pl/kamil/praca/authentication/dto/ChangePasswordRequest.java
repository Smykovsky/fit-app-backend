package pl.kamil.praca.authentication.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String newPassword;
    private String newPasswordConfirmed;
}
