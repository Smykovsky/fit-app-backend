package pl.kamil.praca.authentication.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.model.Meal;

import java.util.List;

@Getter
public class UserDietViewModel {
    private final Long id;
    private final String username;
    private final List<Meal> meals;
    private final Double caloriesEaten;
    private final Double protein;
    private final Double carbohydrates;
    private final Double fat;

    public UserDietViewModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.meals = user.getMeals();
        this.caloriesEaten =
    }


}
