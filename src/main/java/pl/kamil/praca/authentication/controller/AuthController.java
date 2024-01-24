package pl.kamil.praca.authentication.controller;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.dto.*;
import pl.kamil.praca.authentication.model.RefreshToken;
import pl.kamil.praca.authentication.model.Role;
import pl.kamil.praca.authentication.model.Token;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.repository.TokenRepository;
import pl.kamil.praca.authentication.security.JWT.JwtUtil;
import pl.kamil.praca.authentication.service.RefreshTokenService;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.authentication.view.UserViewModel;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController{
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        Map<String, Object> responseMap = new HashMap<>();

        if (!registerRequest.getPassword().equals(registerRequest.getPassword_confirmed())) {
            responseMap.put("message", "Hasła muszą być takie same!");
            return ResponseEntity.status(401).body(responseMap);
        } else if (userService.existsByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail())) {
            responseMap.put("message", "Taki użytkownik już istnieje! Wprowadź inną nazwę użytkownika lub email!");
            return ResponseEntity.status(401).body(responseMap);
        } else {
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setBlocked(false);
            user.setUsername(registerRequest.getUsername());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            Role role = userService.findRoleByName("user");
            user.addRole(role);
            userService.saveUser(user);
            responseMap.put("message", "Konto: " + registerRequest.getUsername() +" zostało pomyślnie zalożone");
            return ResponseEntity.ok(responseMap);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        final User user = userService.getUser(loginRequest.getUsername());
        if (user == null) {
            responseMap.put("message", "Brak takiego użytkownika");
            return ResponseEntity.status(401).body(responseMap);
        }

        if (user.isBlocked()) {
          responseMap.put("message", "Konto zostało zablokowane!");
          return ResponseEntity.status(403).body(responseMap);
        }
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (auth.isAuthenticated()) {
                UserDetails userDetails = userService.getUserDetails(user);
                if (userDetails == null) {
                    responseMap.put("message", "Brak takiego użytkownika w systemie!");
                    return ResponseEntity.status(401).body(responseMap);
                }
                String token = jwtUtil.buildJwt(userDetails);
                tokenRepository.save(new Token(null, token, user.getUsername()));
                final RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(user.getUsername());
                responseMap.put("message", "Pomyślnie zalogowano użytkownika: " + loginRequest.getUsername());
                responseMap.put("access_token", token);
                responseMap.put("refresh_token", refreshToken.getToken());
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("message", "Błędne hasło lub login");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            responseMap.put("message", "Użytkownik zablokowany");
            e.printStackTrace();
            return ResponseEntity.status(500).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("message", "Błędne hasło lub login");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            responseMap.put("message", "Błąd");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String header) {
        final String token = header.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        final User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

      UserViewModel userViewModel = new UserViewModel(user);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", userViewModel);
        return ResponseEntity.ok().body(responseMap);
    }

    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String header) {

        final String token = header.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        final User user = userService.getUser(username);

        Map<String, Object> responseMap = new HashMap<>();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        responseMap.put("message", "Wylogowano!");
        return ResponseEntity.ok().body(responseMap);
    }

    @PostMapping("/refresh")
    @Transactional
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        String requestRefreshToken = refreshTokenRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUsername)
                .map(username -> {
                    final var user = this.userService.getUserDetails(username);
                    final String token = jwtUtil.buildJwt(user);
                    this.tokenRepository.save(new Token(null, token, username));
                    responseMap.put("access_token", token);
                    responseMap.put("refresh_token", requestRefreshToken);
                    return ResponseEntity.ok(responseMap);
                })
                .orElseGet(() -> {
                    responseMap.put("message", "RefreshToken wygasł lub nie istnieje!");
                    return ResponseEntity.status(500).body(responseMap);
                });
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestBody NewPasswordRequest newPasswordRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            responseMap.put("message", "Nieodnaleziono takiego użytkownika!");
            return ResponseEntity.status(401).body(responseMap);
        }
        if (!passwordEncoder.matches(newPasswordRequest.getOldPassword(), user.getPassword())) {
            responseMap.put("message", "Stare hasło jest błędne!");
            System.out.println(passwordEncoder.matches(newPasswordRequest.getOldPassword(), user.getPassword()));
            return ResponseEntity.status(401).body(responseMap);
        }

        if (!newPasswordRequest.getNewPassword().equals(newPasswordRequest.getNewPasswordConfirmed())) {
            responseMap.put("message", "Hasła się nie zgadzają!");
        }

        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
        userService.saveUser(user);
        responseMap.put("message", "Hasło zostało zmienione!");
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping("/passwordRecovery")
    public ResponseEntity<?> passwordRecovery (@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        final User user = userService.getUser(passwordRecoveryRequest.getUsername(), passwordRecoveryRequest.getEmail());
        if (user == null) {
            responseMap.put("message", "Nieodnaleziono takiego użytkownika!");
            return ResponseEntity.status(401).body(responseMap);
        }
        String randomPassword = userService.randomPasswordGenerator();
        user.setPassword(passwordEncoder.encode(randomPassword));
        userService.saveUser(user);
        userService.sendEmail(passwordRecoveryRequest.getEmail(), randomPassword);
        responseMap.put("message", "Wysłano !");
        return ResponseEntity.ok(responseMap);
    }
}
