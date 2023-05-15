package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeRequest {
    @Nullable
    private Long id;
    private String name;
    private String description;
    private String instruction;
    private Double kcal;
    private Double carbohydrates;
    private Double protein;
    private Double fat;

}
