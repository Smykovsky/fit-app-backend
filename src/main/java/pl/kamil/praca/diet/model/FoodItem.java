package pl.kamil.praca.diet.model;

import javax.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import pl.kamil.praca.diet.dto.FoodItemRequest;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;


    public FoodItem(FoodItemRequest foodItemDto) {
        this.id = null;
        this.name = foodItemDto.getName();
        this.calories = foodItemDto.getCalories();
        this.protein = foodItemDto.getProtein();
        this.carbohydrates = foodItemDto.getCarbohydrates();
        this.fat = foodItemDto.getFat();
    }

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
