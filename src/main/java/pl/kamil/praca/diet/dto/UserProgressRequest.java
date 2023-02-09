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
    @Nullable
    private LocalDate date;

    private Double newWeight;

    public UserProgressRequest(Double newWeight) {
        this.date = LocalDate.now();
        this.newWeight = newWeight;
    }
}
