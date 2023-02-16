package pl.kamil.praca.diet.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import pl.kamil.praca.diet.dto.FoodItemRequest;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;

    public FoodItem(FoodItemRequest foodItemDto) {
        this.id = null;
        this.name = foodItemDto.getName();
        this.calories = foodItemDto.getCalories();
        this.protein = foodItemDto.getProtein();
        this.carbohydrates = foodItemDto.getCarbohydrates();
        this.fat = foodItemDto.getFat();
    }
}
