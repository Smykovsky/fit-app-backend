package pl.kamil.praca.diet.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double newWeight;

    public UserProgress(Double newWeight) {
        this.date = LocalDate.now();
        this.newWeight = newWeight;
    }
}
