package pl.kamil.praca.authentication.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.model.Meal;
import pl.kamil.praca.diet.model.UserProgress;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @Nullable
    private int age;
    @Nullable
    private String gender;
    @Nullable
    private Double weight;
    @Nullable
    private Double height;
    @Nullable
    private String goal;
    @Nullable
    private Double calorieIntakeGoal;
    @Nullable
    private LocalDateTime joinedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Meal> meals; // meals list -> breakfast, second_breakfast, lunch, dinner

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FoodItem> foodItems; //meals items

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserProgress> progressList; // user's progress list

    public Collection<GrantedAuthority> roleToAuthority() {
        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        this.roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return grantedAuthorities;
    }

    //meals methods
    public void addMeal(Meal meal) {
        this.meals.add(meal);
    }
    public void removeMeal(Meal meal) {
        this.meals.remove(meal);
    }
    public void removeMeal(Long id) {
        this.meals.removeIf(meal -> meal.getId().equals(id));
    }
    public Meal getMeal(Long id) {
        return this.meals.stream().filter(meal -> meal.getId().equals(id)).findFirst().orElse(null);
    }

    //user progress methods
    public void addUserProgress(UserProgress userProgress){
        this.progressList.add(userProgress);
    }
    public void removeUserProgress(UserProgress userProgress) {
        this.progressList.remove(userProgress);
    }
    public void removeUserProgress(Long id) {
        this.progressList.removeIf(progress -> progress.getId().equals(id));
    }
    public UserProgress getUserProgress(Long id) {
        return this.progressList.stream().filter(progress -> progress.getId().equals(id)).findFirst().orElse(null);
    }

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
