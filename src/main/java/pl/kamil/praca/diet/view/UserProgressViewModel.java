package pl.kamil.praca.diet.view;

import lombok.Getter;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.diet.model.UserProgress;

@Getter
public class UserProgressViewModel {
    private Double lastWeight;
    private Double newWeight;
    private Double progress;
    private boolean isProgress;

    public UserProgressViewModel(final UserProgress userProgress, User user) {
        this.lastWeight = user.getWeight();
        this.newWeight = userProgress.getNewWeight();
        this.progress = user.getWeight() - userProgress.getNewWeight();
        this.isProgress = (isProgress ? this.progress > 0 : this.progress < 0);
    }
}
