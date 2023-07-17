package pl.kamil.praca.authentication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamil.praca.authentication.model.Role;
import pl.kamil.praca.authentication.repository.RoleRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {
    private final RoleRepository roleRepository;

    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        List<Role> all = roleRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
