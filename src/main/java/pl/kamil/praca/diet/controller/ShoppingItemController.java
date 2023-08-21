package pl.kamil.praca.diet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.model.ShoppingItem;
import pl.kamil.praca.diet.service.ShoppingItemService;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingItem")
@RequiredArgsConstructor
public class ShoppingItemController {
    private final ShoppingItemService shoppingItemService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addShoppingItem(Authentication authentication, @RequestBody String name) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return this.shoppingItemService.addShoppingItem(authentication.getName(), name);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getShoppingItems(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<ShoppingItem> shoppingItemList = user.getShoppingItemList();
        return ResponseEntity.ok(shoppingItemList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getShoppingItemById(Authentication authentication, @PathVariable Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        ShoppingItem byId = this.shoppingItemService.getById(id);
        return ResponseEntity.ok(byId);
    }
}
