package pl.kamil.praca.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import pl.kamil.praca.diet.model.ShoppingItemStatus;

@AllArgsConstructor
@Getter
public class ShoppingItemRequest {
    @Nullable
    private Long id;
    private String name;
    @Nullable
    private ShoppingItemStatus status;
}
