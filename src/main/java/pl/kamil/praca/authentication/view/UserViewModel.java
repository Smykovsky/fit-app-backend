package pl.kamil.praca.authentication.view;

import lombok.Getter;
import pl.kamil.praca.authentication.model.Role;
import pl.kamil.praca.authentication.model.User;

import java.util.List;

@Getter
public class UserViewModel {
    private final Long id;
    private final String email;
    private final String username;
    private final boolean isBlocked;
    private final List<String> roles;

    public UserViewModel(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.isBlocked = user.isBlocked();
        this.roles = user.getRoles().stream().map(Role::getName).toList();
    }
}
