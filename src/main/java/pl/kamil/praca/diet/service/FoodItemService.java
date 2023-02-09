package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.model.Meal;
import pl.kamil.praca.diet.repository.FoodItemRepository;
import pl.kamil.praca.diet.repository.MealRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final MealService mealService;
    private final UserService userService;
    private final MealRepository mealRepository;


    public void save(FoodItem foodItem) {
        this.foodItemRepository.save(foodItem);
    }

    public void addFoodItem(final User user, final FoodItem foodItem, Meal meal) {
        if (meal == null) {
            return;
        }
        this.save(foodItem);
        meal.addFoodItems(foodItem);
        this.mealService.save(meal);
        this.userService.saveUser(user);
    }

    public List<FoodItem> getAll() {
        return this.foodItemRepository.findAll();
    }
    public FoodItem get(Long id) {
        return this.foodItemRepository.findById(id).orElse(null);
    }

    public List<FoodItem> getAll(PageRequest pageRequest) {
        return this.foodItemRepository.findAllBy(pageRequest);
    }

    public void delete(Long mealId, Long itemId) {
        Meal meal = mealRepository.findById(mealId).orElse(null);
        Optional<FoodItem> foodItem = foodItemRepository.findById(itemId);
        if (foodItem.isPresent()) {
            meal.removeFoodItems(itemId);
            foodItemRepository.deleteById(itemId);
            mealRepository.save(meal);
        }
    }

    public void delete(FoodItem foodItem) {
        this.foodItemRepository.delete(foodItem);
    }

    public void getCaloriesFromUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }
        User user = userService.getUser(authentication.getName());
        List<Meal> meals = user.getMeals();
        double sum = meals.stream()
                .map(Meal::getFoodItems)
                .map(foodItems -> foodItems.stream().map(foodItem -> foodItem.getCalories()).reduce(0.0, Double::sum))
                .reduce(0.0, Double::sum);
    }

}
