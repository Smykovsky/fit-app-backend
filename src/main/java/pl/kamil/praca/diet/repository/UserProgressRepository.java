package pl.kamil.praca.diet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamil.praca.diet.model.UserProgress;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
}
