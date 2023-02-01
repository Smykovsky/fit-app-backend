package pl.kamil.praca.authentication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.RefreshToken;
import pl.kamil.praca.authentication.repository.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Optional<RefreshToken> findByToken(final String token) {
        return this.refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(final String username) {
        return this.refreshTokenRepository.save(
                new RefreshToken(null, UUID.randomUUID().toString(), username, LocalDateTime.now().plusDays(30))
        );
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.deleteById(token.getId());
            return null;
        }
        return token;
    }


    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }
}
