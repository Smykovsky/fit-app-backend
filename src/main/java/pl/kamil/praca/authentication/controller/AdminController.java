package pl.kamil.praca.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.dto.NewPasswordRequest;
import pl.kamil.praca.authentication.dto.RoleRequest;
import pl.kamil.praca.authentication.model.Role;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.repository.RoleRepository;
import pl.kamil.praca.authentication.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        List<Role> all = roleRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> addRole (Authentication authentication, @RequestBody RoleRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        User user = userService.getUser(request.getUsername());
        Role roleByName = roleRepository.findByName(request.getRole());
        if (user.getRoles().contains(roleByName)) {
            return ResponseEntity.noContent().build();
        }
        user.addRole(roleByName);
        userService.saveUser(user);
        return ResponseEntity.ok("Dodano role: " + request.getRole());
    }

    @PostMapping("/removeRole")
    public ResponseEntity<?> removeRole(Authentication authentication, @RequestBody RoleRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        User user = userService.getUser(request.getUsername());
        Role roleByName = roleRepository.findByName(request.getRole());

        if (user.getRoles().contains(roleByName)) {
            user.removeRole(roleByName);
            userService.saveUser(user);
            return ResponseEntity.ok("Usunięto role");
        }

        return ResponseEntity.ok("Użytkownik nie ma takiej roli");
    }

    @PostMapping("/{username}/updatePassword")
    public ResponseEntity<?> udpatePasswordForUser(Authentication authentication, @PathVariable String username, @RequestBody String newPassword) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        User user = userService.getUser(username);
        if (user == null){
            return ResponseEntity.notFound().build();
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);
        userService.sendEmail(user.getEmail(), newPassword);
        return ResponseEntity.noContent().build();
    }
}
