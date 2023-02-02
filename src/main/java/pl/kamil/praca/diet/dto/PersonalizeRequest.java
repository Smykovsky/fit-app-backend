package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PersonalizeRequest {
    @Nullable
    private int age;
    @Nullable
    private Double weight;
    @Nullable
    private Double height;
}
