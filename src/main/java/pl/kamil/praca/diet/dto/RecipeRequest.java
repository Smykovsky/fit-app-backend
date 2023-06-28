package pl.kamil.praca.diet.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
