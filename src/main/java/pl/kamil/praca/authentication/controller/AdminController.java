package pl.kamil.praca.authentication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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
}
