package pl.kamil.praca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamil.praca.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
