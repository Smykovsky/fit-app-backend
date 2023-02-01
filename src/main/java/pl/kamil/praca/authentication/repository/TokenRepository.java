package pl.kamil.praca.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamil.praca.authentication.model.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findTokenByTokenAndUsername(String token, String username);

    Optional<Token> findTokenByToken(String token);
    void deleteTokenByToken(String token);

    Token save(Token token);
    void deleteById(Long id);
}
