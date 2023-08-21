package pl.kamil.praca.diet.model;

import lombok.*;
import pl.kamil.praca.diet.dto.ShoppingItemRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Data
public class ShoppingItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private ShoppingItemStatus status;

    public ShoppingItem(ShoppingItemRequest shoppingItemRequest) {
        this.id = null;
        this.name = shoppingItemRequest.getName();
        this.setStatus(ShoppingItemStatus.UNCHECKED);
    }

}
