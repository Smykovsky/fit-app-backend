package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.model.Meal;
import pl.kamil.praca.diet.repository.FoodItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final MealService mealService;
    private final UserService userService;


    public void save(FoodItem foodItem) {
        this.foodItemRepository.save(foodItem);
    }

    public void addFoodItem(final FoodItem foodItem, Meal meal) {
        if (meal == null) {
            return;
        }
        meal.addFoodItems(foodItem);
        this.mealService.save(meal);
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

    public void delete(Long id) {
        this.foodItemRepository.deleteById(id);
    }
    public void delete(FoodItem foodItem) {
        this.foodItemRepository.delete(foodItem);
    }
}
