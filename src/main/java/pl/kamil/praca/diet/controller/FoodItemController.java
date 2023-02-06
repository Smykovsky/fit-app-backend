package pl.kamil.praca.diet.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.FoodItemRequest;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.service.FoodItemService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foodItems")
public class FoodItemController {
    private final FoodItemService foodItemService;
    private final UserService userService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?>addFoodItem(Authentication authentication, @RequestBody @Valid FoodItemRequest foodItemRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        this.foodItemService.addFoodItem(new FoodItem(foodItemRequest), user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    ResponseEntity<?>getFoodItem(Authentication authentication, @PathVariable @Valid Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final FoodItem foodItem = user.getFoodItems(id);
        if (foodItem == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foodItem);
    }

    @GetMapping("/get")
    ResponseEntity<?>GetFoodItems(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getFoodItems());
    }

    @PostMapping("/update")
    @Transactional
    ResponseEntity<?>update(Authentication authentication, @RequestBody FoodItemRequest foodItemRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        final FoodItem foodItemToSave = user.getFoodItems(foodItemRequest.getId());
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
    ResponseEntity<?> delete(Authentication authentication, @RequestBody String json) throws JSONException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final JSONObject jsonObject = new JSONObject(json);
        final long id = jsonObject.getLong("id");
        final FoodItem foodItemToRemove = user.getFoodItems(id);
        if (foodItemToRemove == null) {
            return ResponseEntity.notFound().build();
        }
        user.removeFoodItems(foodItemToRemove);
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }
}
