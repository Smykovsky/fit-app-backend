package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class MealRequest {
    @Nullable
    private Long id;
    private Long foodItemId;
    private String name;

}
