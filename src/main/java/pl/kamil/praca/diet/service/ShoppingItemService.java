package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.ShoppingItemRequest;
import pl.kamil.praca.diet.model.ShoppingItem;
import pl.kamil.praca.diet.model.ShoppingItemStatus;
import pl.kamil.praca.diet.repository.ShoppingItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingItemService {
    private final UserService userService;
    private final ShoppingItemRepository shoppingItemRepository;

    public void save(ShoppingItem item) {
        this.shoppingItemRepository.save(item);
    }

    public ShoppingItem getById(Long id) {
        return this.shoppingItemRepository.findById(id).orElse(null);
    }

    public List<ShoppingItem> getAll() {
        return this.shoppingItemRepository.findAll();
    }

    public ResponseEntity<?> addShoppingItem(final String username, ShoppingItemRequest request) {
        final User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        ShoppingItem shoppingItem = new ShoppingItem(request);
        user.addShoppingItem(shoppingItem);
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> editShoppingItem(final String username, Long id, String newName) {
        final User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        ShoppingItem shoppingItemById = this.getById(id);
        if (shoppingItemById == null) {
            return ResponseEntity.notFound().build();
        }

        shoppingItemById.setName(newName);
        this.shoppingItemRepository.save(shoppingItemById);
        return ResponseEntity.noContent().build();
    }

    public void deleteShoppingItem(Long id) {
        ShoppingItem deletedItem = this.getById(id);
        this.shoppingItemRepository.delete(deletedItem);
    }

    public void setStatus(Long id) {
        ShoppingItem byId = this.getById(id);
        if (byId.getStatus().equals(ShoppingItemStatus.UNCHECKED)) {
            byId.setStatus(ShoppingItemStatus.CHECKED);
        } else byId.setStatus(ShoppingItemStatus.UNCHECKED);
        this.shoppingItemRepository.save(byId);
    }
}
