package pl.kamil.praca.diet.model;

public enum MealType {
    BREAKFAST("Śniadanie"),
    SECOND_BREAKFAST("Drugie śniadanie"),
    LUNCH("Obiad"),
    DINNER("Kolacja");

    private String description;

    MealType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
