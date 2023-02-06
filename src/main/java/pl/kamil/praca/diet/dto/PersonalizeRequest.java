package pl.kamil.praca.diet.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalizeRequest {
    private int age;
    private Double weight;
    private Double height;
    private String gender;
    @Nullable
    private String goal;
}
