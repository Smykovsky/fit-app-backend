package pl.kamil.praca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamil.praca.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
