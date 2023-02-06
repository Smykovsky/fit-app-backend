package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.model.FoodItem;
import pl.kamil.praca.diet.repository.FoodItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;
    private final UserService userService;


    public void save(FoodItem foodItem) {
        this.foodItemRepository.save(foodItem);
    }

    public void addFoodItem(final FoodItem foodItem, User user) {
        if (user == null) {
            return;
        }
        user.addFoodItems(foodItem);
        this.userService.saveUser(user);
    }

    public List<FoodItem> getAll() {
        return this.foodItemRepository.findAll();
    }

    public List<FoodItem> getAll(PageRequest pageRequest) {
        return this.foodItemRepository.findAllBy(pageRequest);
    }

    public void delete(Long id) {
        this.foodItemRepository.deleteById(id);
    }
}
