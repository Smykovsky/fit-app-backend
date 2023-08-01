package pl.kamil.praca.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kamil.praca.authentication.model.Role;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.repository.RoleRepository;
import pl.kamil.praca.authentication.service.UserService;

@Component
public class RoleInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.existsByName("user") && !roleRepository.existsByName("admin")) {
            Role userRole = new Role("user");
            roleRepository.save(userRole);
            Role adminRole = new Role("admin");
            roleRepository.save(adminRole);
            Role modRole = new Role("mod");
            roleRepository.save(modRole);
        } else {
           return;
        }

        if (userService.getUser("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.pl");
            Role roleForAdmin = roleRepository.findByName("admin");
            admin.addRole(roleForAdmin);
            userService.saveUser(admin);
        } else {
            return;
        }


    }
}

