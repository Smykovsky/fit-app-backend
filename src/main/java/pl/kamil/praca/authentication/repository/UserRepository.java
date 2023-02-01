package pl.kamil.praca.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamil.praca.authentication.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String Username);
   Optional<User> findByEmail(String email);
   User findByUsernameOrEmail(String name, String email);
   boolean existsByUsernameOrEmail(final String name, final String email);
}
