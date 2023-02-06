package pl.kamil.praca.diet.view;

import lombok.Getter;

@Getter
public class UserProgressViewModel {
    private Double lastWeight;
    private Double newWeight;
    private Double progress;
    private boolean isProgress;
}
