package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class UserProgressRequest {
    @Nullable
    private Long id;

    private LocalDate date;

    private Double newWeight;

    public UserProgressRequest(Double newWeight) {
        this.date = LocalDate.now();
        this.newWeight = newWeight;
    }
}
