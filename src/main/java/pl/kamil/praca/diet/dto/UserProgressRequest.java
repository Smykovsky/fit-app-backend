package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserProgressRequest {
    @Nullable
    private Long id;

    private Double newWeight;

    public UserProgressRequest(Double newWeight) {
        this.newWeight = newWeight;
    }
}
