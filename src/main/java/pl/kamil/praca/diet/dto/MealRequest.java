package pl.kamil.praca.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;


@AllArgsConstructor
@Getter
public class MealRequest {
    @Nullable
    private Long id;
    @Nullable
    private Long foodItemId;
    private String name;

}
