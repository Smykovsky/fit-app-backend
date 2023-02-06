package pl.kamil.praca.diet.model;

import jakarta.persistence.*;
import lombok.*;
import pl.kamil.praca.diet.dto.UserProgressRequest;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Data
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private LocalDate date;

    private Double newWeight;

    public UserProgress(UserProgressRequest userProgressRequest) {
        this.date = LocalDate.now();
        this.newWeight = userProgressRequest.getNewWeight();
    }
}
