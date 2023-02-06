package pl.kamil.praca.diet.view;

import lombok.Getter;
import pl.kamil.praca.diet.model.Meal;


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
