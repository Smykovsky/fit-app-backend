package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Data
@Getter
public class MealRequest {
    private Long id;
    private Long foodItemId;
    private String name;

}
