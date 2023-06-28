package pl.kamil.praca.diet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class FoodItemRequest {
    @Nullable
    private Long id;
    @Nullable
    private Long mealId;
    @NotBlank
    private String name;
    @NotBlank
    private Double calories;
    @NotBlank
    private Double protein;
    @NotBlank
    private Double carbohydrates;
    @NotBlank
    private Double fat;
}
