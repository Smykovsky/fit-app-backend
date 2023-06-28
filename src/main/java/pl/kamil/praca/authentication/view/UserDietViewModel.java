package pl.kamil.praca.authentication.view;

import lombok.Getter;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.diet.model.Meal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Getter
public class UserDietViewModel {
    private final Long id;
    private final String username;
    private final Double weight;
    private final String goal;
    private final List<Meal> meals;
    private final Double caloriesIntakeGoal;
    private final Double caloriesEaten;
    private final Double proteins;
    private final Double carbohydrates;
    private final Double fats;
    private final String today;

    public UserDietViewModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.weight = user.getWeight();
        this.goal = user.getGoal();
        this.caloriesIntakeGoal = user.getCalorieIntakeGoal();
        this.meals = user.getMeals();
        this.caloriesEaten = user.getEatenCalories();
        this.proteins = user.getEatenProteins();
        this.carbohydrates = user.getEatenCarbohydrates();
        this.fats = user.getEatenFats();
        this.today = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }


}
