package pl.kamil.praca.diet.controller;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.FoodItemRequest;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.model.Meal;
import pl.kamil.praca.diet.service.FoodItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/item")
public class FoodItemController {
    private final FoodItemService foodItemService;
    private final UserService userService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?>addFoodItem(Authentication authentication, @RequestBody FoodItemRequest foodItemRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final Meal meal = user.getMeal(foodItemRequest.getMealId());
        if (meal == null) {
            return ResponseEntity.notFound().build();
        }
        this.foodItemService.addFoodItem(user,new FoodItem(foodItemRequest), meal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>getFoodItem(Authentication authentication, @PathVariable @Valid Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }


        final FoodItem foodItem = foodItemService.get(id);
        if (foodItem == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foodItem);
    }

    @GetMapping("/get")
    public ResponseEntity<?>getFoodItems(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<FoodItem> foodItems = user.getFoodItemsPerDay();

//        List<FoodItem> foodItemList = this.foodItemService.getAll();
        return ResponseEntity.ok(foodItems);


    }

    @PostMapping("/update")
    @Transactional
    public ResponseEntity<?>updateFoodItem(Authentication authentication, @RequestBody FoodItemRequest foodItemRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final FoodItem foodItemToSave = foodItemService.get(foodItemRequest.getId());
        if (foodItemToSave == null) {
            return ResponseEntity.notFound().build();
        }

        foodItemToSave.setName(foodItemRequest.getName());
        foodItemToSave.setCalories(foodItemRequest.getCalories());
        foodItemToSave.setFat(foodItemRequest.getProtein());
        foodItemToSave.setCarbohydrates(foodItemRequest.getCarbohydrates());
        foodItemToSave.setFat(foodItemRequest.getFat());
        this.foodItemService.save(foodItemToSave);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/delete")
    @Transactional
    public ResponseEntity<?> deleteFoodItem(Authentication authentication, @RequestBody String json) throws JSONException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final JSONObject jsonObject = new JSONObject(json);
        final long mealId = jsonObject.getLong("mealId");
        final long itemId = jsonObject.getLong("itemId");

        this.foodItemService.delete(mealId, itemId);
        return ResponseEntity.noContent().build();
    }
}
