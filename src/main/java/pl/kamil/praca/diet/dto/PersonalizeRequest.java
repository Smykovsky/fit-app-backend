package pl.kamil.praca.diet.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
