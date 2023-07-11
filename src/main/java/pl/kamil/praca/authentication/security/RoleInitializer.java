package pl.kamil.praca.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kamil.praca.authentication.model.Role;
import pl.kamil.praca.authentication.repository.RoleRepository;

@Component
public class RoleInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!roleRepository.existsByName("user") && !roleRepository.existsByName("admin")) {
            Role userRole = new Role("user");
            roleRepository.save(userRole);
            Role adminRole = new Role("admin");
            roleRepository.save(adminRole);
        } else {
           return;
        }


    }
}

