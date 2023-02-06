package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class FoodItemRequest {
    @Nullable
    private Long id;
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
