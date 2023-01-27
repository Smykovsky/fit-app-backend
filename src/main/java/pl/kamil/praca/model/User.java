package pl.kamil.praca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    private String password;
    private int age;
    private String gender;
    private Double weight;
    private Double height;
    private String goal;
    private Double calorieIntakeGoal;
    private LocalDateTime joinedDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Meal> meals;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserProgress> progressList;

}
