package pl.kamil.praca.diet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.ShoppingItemRequest;
import pl.kamil.praca.diet.model.ShoppingItem;
import pl.kamil.praca.diet.service.ShoppingItemService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/shoppingItem")
@RequiredArgsConstructor
public class ShoppingItemController {
    private final ShoppingItemService shoppingItemService;
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addShoppingItem(Authentication authentication, @RequestBody ShoppingItemRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return this.shoppingItemService.addShoppingItem(authentication.getName(), request);
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
    @PostMapping("/edit/{id}")
    @Transactional
    public ResponseEntity<?> editShoppingItemById(Authentication authentication, @PathVariable("id") Long id, @RequestBody ShoppingItemRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        ShoppingItem byId = this.shoppingItemService.getById(id);
        byId.setName(request.getName());
        shoppingItemService.save(byId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set/status/{id}")
    @Transactional
    public ResponseEntity<?> setStatus(Authentication authentication, @PathVariable("id") Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        shoppingItemService.setStatus(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable("id") Long id) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        shoppingItemService.deleteShoppingItem(user, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete/all")
    public ResponseEntity<?> deleteAll(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        final User user = userService.getUser(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        shoppingItemService.deleteAllShoppingItems(user);
        return ResponseEntity.noContent().build();
    }
}
