package pl.kamil.praca.diet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.diet.dto.RecipeRequest;
import pl.kamil.praca.diet.model.Recipe;
import pl.kamil.praca.diet.service.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/add")
    public ResponseEntity<?>addRecipe(Authentication authentication, @RequestBody @Valid RecipeRequest recipeRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        this.recipeService.addRecipe(new Recipe(recipeRequest));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<?>get(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        List<Recipe> recipeList = recipeService.getAll();
        return ResponseEntity.ok(recipeList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>getFoodItem(Authentication authentication, @PathVariable @Valid Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(Authentication authentication, @RequestBody @Valid RecipeRequest recipeRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final Recipe recipeToSave = recipeService.getRecipe(recipeRequest.getId());
        if (recipeToSave == null) {
            return ResponseEntity.notFound().build();
        }

        recipeToSave.setName(recipeRequest.getName());
        recipeToSave.setDescription(recipeRequest.getDescription());
        recipeToSave.setKcal(recipeRequest.getKcal());
        recipeToSave.setCarbohydrates(recipeRequest.getCarbohydrates());
        recipeToSave.setProtein(recipeRequest.getProtein());
        recipeToSave.setFat(recipeRequest.getFat());
        this.recipeService.save(recipeToSave);

        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(Authentication authentication, @RequestBody String json) throws JSONException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }

        final JSONObject jsonObject = new JSONObject(json);
        final Long recipeId = jsonObject.getLong("id");

        this.recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }
}
