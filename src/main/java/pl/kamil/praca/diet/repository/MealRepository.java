package pl.kamil.praca.diet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.model.Meal;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByCreatedAt(LocalDate date);
//    List<Meal>findByDate(LocalDate createdAt);
}
