package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kamil.praca.diet.dto.RecipeRequest;
import pl.kamil.praca.diet.model.Recipe;
import pl.kamil.praca.diet.repository.RecipeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public void save(Recipe recipe) {
        this.recipeRepository.save(recipe);
    }

    public List<Recipe> getAll() {
        return this.recipeRepository.findAll();
    }

    public Recipe getRecipe(Long id) {
        return this.recipeRepository.findById(id).orElse(null);
    }

    public void addRecipe(Recipe recipe) {
        this.save(recipe);
    }

    public ResponseEntity<?> edit(final RecipeRequest recipeRequest) {
        final Recipe oldRecipe = recipeRepository.findById(recipeRequest.getId()).orElse(null);
        if (oldRecipe == null) {
            return ResponseEntity.notFound().build();
        }

        final Recipe recipe = new Recipe(recipeRequest);
        this.recipeRepository.save(recipe);
        return ResponseEntity.noContent().build();
    }

    public void delete(Long id) {
        this.recipeRepository.deleteById(id);
    }
}
