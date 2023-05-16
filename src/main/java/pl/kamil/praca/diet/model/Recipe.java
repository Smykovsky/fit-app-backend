package pl.kamil.praca.diet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kamil.praca.diet.dto.RecipeRequest;

import java.util.List;

@Entity
@Table(name = "recipes")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Recipe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(length = 500)
    private String instruction;
    private Double kcal;
    private Double carbohydrates;
    private Double protein;
    private Double fat;


    public Recipe(RecipeRequest recipeRequest) {
        this.id = null;
        this.name = recipeRequest.getName();
        this.description = recipeRequest.getDescription();
        this.instruction = recipeRequest.getInstruction();
        this.kcal = recipeRequest.getKcal();
        this.carbohydrates = recipeRequest.getCarbohydrates();
        this.protein = recipeRequest.getProtein();
        this.fat = recipeRequest.getFat();
    }
}
