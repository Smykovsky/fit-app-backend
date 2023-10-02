package pl.kamil.praca.diet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.authentication.view.UserDietViewModel;
import pl.kamil.praca.authentication.view.UserViewModel;
import pl.kamil.praca.diet.dto.PersonalizeRequest;
import pl.kamil.praca.diet.model.UserProgress;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/personalize")
    public ResponseEntity<?> personalizeUser(@RequestBody @Valid PersonalizeRequest personalizeRequest, Authentication authentication) {
        Map<String, Object> responseMap = new HashMap<>();
        final String MAN = "Mężczyzna";
        final String WOMEN = "Kobieta";

        if (authentication == null || !authentication.isAuthenticated()) {
            responseMap.put("message", "Użytkownik nie jest zautoryzowany!");
            return ResponseEntity.status(500).body(responseMap);
        }

        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setAge(personalizeRequest.getAge());
        user.setWeight(personalizeRequest.getWeight());
        user.setHeight(personalizeRequest.getHeight());
        user.setGender(personalizeRequest.getGender());
        user.setGoal(personalizeRequest.getGoal());
        user.setActivity(personalizeRequest.getActivity());

        user.addUserProgress(new UserProgress(personalizeRequest.getWeight()));

        if (personalizeRequest.getGender().equals(MAN)) {
            user.setCalorieIntakeGoal(userService.calculateCaloriesMan(user));
            userService.saveUser(user);
        } else
            user.setCalorieIntakeGoal(userService.calculateCaloriesWomen(user));
        userService.saveUser(user);

        responseMap.put("user", user);
        responseMap.put("message", "Pomyślnie spersonalizowano użytkownika.");
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getUsersList(Authentication authentication) {
        Map<String, Object> responseMap = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            responseMap.put("message", "Użytkownik nie jest zautoryzowany!");
            return ResponseEntity.status(500).body(responseMap);
        }

        boolean isAdmin = false;
        final User user = userService.getUser(authentication.getName());
        List<String> roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        for (String value : roles) {
            if (value.equals("admin")) {
                isAdmin = true;
                break;
            }
        }

        if (!isAdmin) {
            return ResponseEntity.notFound().build();
        }

        List<UserViewModel> usersList = userService.getUsers().stream().map(UserViewModel::new).toList();
        return ResponseEntity.ok(usersList);
    }

        @GetMapping("/data")
        public ResponseEntity<?> getUserData(Authentication authentication) {
            Map<String, Object> responseMap = new HashMap<>();
            if (authentication == null || !authentication.isAuthenticated()) {
                responseMap.put("message", "Użytkownik nie jest zautoryzowany!");
                return ResponseEntity.status(500).body(responseMap);
            }

            final User user = userService.getUser(authentication.getName());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(new UserDietViewModel(user));
        }
}
