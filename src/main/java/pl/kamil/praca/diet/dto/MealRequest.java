package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Data
@Getter
@Setter
public class MealRequest {
    private Long id;
    private String name;
    private List<FoodItemDto> foodItems;
@AllArgsConstructor
@Getter
    public static class FoodItemDto {
        @Nullable
        private Long id;
        private String name;
        private Double calories;
        private Double protein;
        private Double carbohydrates;
        private Double fat;
    }
}
