package pl.kamil.praca.diet.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
public class UserProgressRequest {
    @Nullable
    private Long id;
    private Double newWeight;
    @Nullable
    private LocalDate date;

}
