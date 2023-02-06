package pl.kamil.praca.diet.view;

import lombok.Getter;
import pl.kamil.praca.diet.dto.FoodItemRequest;
import pl.kamil.praca.diet.dto.MealRequest;
import pl.kamil.praca.diet.model.Meal;

import java.util.List;

@Getter
public class MealViewModel {
    private final Long id;
    private final String name;
    private final String foodItemName;


    public MealViewModel(final Meal meal) {
        this.id = meal.getId();
        this.name = meal.getName();
        this.foodItemName = meal.getFoodItems().getName();

    }
}