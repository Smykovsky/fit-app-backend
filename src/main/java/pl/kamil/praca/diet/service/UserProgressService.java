package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.model.UserProgress;
import pl.kamil.praca.diet.repository.UserProgressRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private final UserService userService;

    public void save(UserProgress userProgress) {
        this.userProgressRepository.save(userProgress);
    }

    public void addUserProgress(final UserProgress userProgress, User user) {
        if (user == null) {
            return;
        }
        user.addUserProgress(this.userProgressRepository.save(userProgress));
        this.userService.saveUser(user);
    }

    public List<UserProgress>getAll() {
        return this.userProgressRepository.findAll();
    }

    public List<UserProgress> getAll(PageRequest pageRequest) {
        return this.userProgressRepository.findAllBy(pageRequest);
    }

    public void delete(Long id) {
        this.userProgressRepository.deleteById(id);
    }
}
