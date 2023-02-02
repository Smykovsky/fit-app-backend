package pl.kamil.praca.diet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meal")
    private List<FoodItem> foodItems;


    //food items methods
    public void addFoodItems(FoodItem foodItem) {
        this.foodItems.add(foodItem);
    }
    public void removeFoodItems(FoodItem foodItem) {
        this.foodItems.remove(foodItem);
    }
    public void removeFoodItems(Long id) {
        this.foodItems.removeIf(foodItem -> foodItem.getId().equals(id));
    }
    public FoodItem getFoodItems(Long id) {
        return this.foodItems.stream().filter(foodItem -> foodItem.getId().equals(id)).findFirst().orElse(null);
    }
}
