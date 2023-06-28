package pl.kamil.praca.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class MealByDateRequest {
    @Nullable
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickedDate;
}
