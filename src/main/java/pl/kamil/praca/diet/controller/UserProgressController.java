package pl.kamil.praca.diet.controller;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.UserProgressRequest;
import pl.kamil.praca.diet.model.UserProgress;
import pl.kamil.praca.diet.service.UserProgressService;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/progress")
public class UserProgressController {
    private final UserProgressService userProgressService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?>addUserProgress(Authentication authentication, @RequestBody @Valid UserProgressRequest userProgressRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        this.userProgressService.addUserProgress(authentication.getName(), userProgressRequest);
        user.setWeight(userProgressRequest.getNewWeight());
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserProgress(Authentication authentication, @PathVariable @Valid Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final UserProgress userProgress = user.getUserProgress(id);
        if (userProgress == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userProgress);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserProgressList(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getProgressList());
    }

    @PostMapping("/update")
    @Transactional
    public ResponseEntity<?>updateUserProgress(Authentication authentication, @RequestBody @Valid UserProgressRequest userProgressRequest) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final UserProgress userProgressToSave = user.getUserProgress(userProgressRequest.getId());
        if (userProgressToSave == null) {
            return ResponseEntity.notFound().build();
        }
        userProgressToSave.setDate(LocalDate.now());
        userProgressToSave.setNewWeight(userProgressRequest.getNewWeight());

        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete")
    @Transactional
    public ResponseEntity<?>deleteUserProgress(Authentication authentication, @RequestBody String json) throws JSONException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = this.userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final JSONObject jsonObject = new JSONObject(json);
        final long id = jsonObject.getLong("id");
        final UserProgress userProgressToRemove = user.getUserProgress(id);
        if (userProgressToRemove == null) {
            return ResponseEntity.notFound().build();
        }

        user.removeUserProgress(userProgressToRemove);
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }













}
