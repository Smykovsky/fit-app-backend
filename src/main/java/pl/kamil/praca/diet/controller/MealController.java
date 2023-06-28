package pl.kamil.praca.diet.controller;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.MealRequest;
import pl.kamil.praca.diet.model.Meal;
import pl.kamil.praca.diet.service.MealService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meal")
public class MealController {
    private final MealService mealService;
    private final UserService userService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> addMeal(Authentication authentication, @RequestBody MealRequest mealRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        return this.mealService.addMeal(authentication.getName(), mealRequest);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMeal(Authentication authentication, @Valid @PathVariable Long id) {
        return this.mealService.getMeal(authentication, id);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMeals(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getMealsPerDay());
    }

    @GetMapping("/getByDate/{pickedDate}")
    public ResponseEntity<?> getMealsByDate(Authentication authentication, @PathVariable LocalDate pickedDate) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Meal> mealsByDate = mealService.findByDate(user, pickedDate);
        return ResponseEntity.ok(mealsByDate);
    }

    @PostMapping("/update")
    @Transactional
    public ResponseEntity<?> updateMeal(Authentication authentication, @RequestBody @Valid MealRequest mealRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        return this.mealService.editMeal(authentication.getName(), mealRequest);
    }

    @PostMapping("/delete")
    @Transactional
    public ResponseEntity<?> deleteMeal(Authentication authentication, @RequestBody String json) throws JSONException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        return this.mealService.deleteMeal(json, authentication);
    }

}
