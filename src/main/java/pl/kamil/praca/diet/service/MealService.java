package pl.kamil.praca.diet.service;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.repository.UserRepository;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.MealRequest;
import pl.kamil.praca.diet.model.Meal;
import pl.kamil.praca.diet.repository.MealRepository;
import pl.kamil.praca.diet.view.MealViewModel;

import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final UserService userService;

    public void save(Meal meal) {
        this.mealRepository.save(meal);
    }

    public List<Meal> getAll() {
        return this.mealRepository.findAll();
    }

    public ResponseEntity<?> addMeal(final String username, final MealRequest mealRequest) {
        final User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final Meal meal = new Meal(mealRequest.getName());
        this.mealRepository.save(meal);

        user.addMeal(meal);
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> editMeal(final String username, final MealRequest mealRequest) {
        final User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final Meal oldMeal = mealRepository.findById(mealRequest.getId()).orElse(null);
        if (oldMeal == null) {
            return ResponseEntity.notFound().build();
        }

        final Meal newMeal = new Meal(mealRequest.getName());
        this.mealRepository.save(newMeal);
        user.removeMeal(mealRequest.getId());
        user.addMeal(newMeal);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getMeal(Authentication authentication, Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final Meal meal = user.getMeal(id);
        if (meal == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MealViewModel(meal));
    }

    public List<Meal> findByDate (User user, LocalDate date) {
        User byUsername = userRepository.findByUsername(user.getUsername());
        List<Meal> mealsPerDay = user.getMealsPerDayByDate(date);
        return mealsPerDay;
    }

    public ResponseEntity<?> deleteMeal(String json, Authentication authentication) throws JSONException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final JSONObject object = new JSONObject(json);

        final long id = object.getLong("id");
        final Meal mealToRemove = this.mealRepository.findById(id).orElse(null);
        if (mealToRemove == null) {
            return ResponseEntity.notFound().build();
        }
        user.removeMeal(mealToRemove);
        mealRepository.delete(mealToRemove);
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }


}
