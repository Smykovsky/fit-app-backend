package pl.kamil.praca.diet.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Data
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private FoodItem foodItems;

    public Meal(String name, FoodItem foodItem) {
        this.name = name;
        this.foodItems = foodItem;
    }



}
