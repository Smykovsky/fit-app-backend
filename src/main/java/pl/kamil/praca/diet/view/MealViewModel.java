package pl.kamil.praca.diet.view;

import lombok.Getter;
import pl.kamil.praca.diet.dto.MealRequest;
import pl.kamil.praca.diet.model.Meal;

import java.util.List;

@Getter
public class MealViewModel {
    private final Long id;
    private final String name;
    private final List<MealRequest.FoodItemDto> foodItems;

    public MealViewModel(final Meal meal) {
        this.id = meal.getId();
        this.name = meal.getName();
        this.foodItems = meal.getFoodItems().stream().map(item -> new MealRequest.FoodItemDto(
                item.getId(),
                item.getName(),
                item.getCalories(),
                item.getProtein(),
                item.getCarbohydrates(),
                item.getFat()))
                .toList();
    }

}
