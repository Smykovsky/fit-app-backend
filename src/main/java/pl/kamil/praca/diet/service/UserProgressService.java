package pl.kamil.praca.diet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kamil.praca.authentication.model.User;
import pl.kamil.praca.authentication.service.UserService;
import pl.kamil.praca.diet.dto.UserProgressRequest;
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

//    public void addUserProgress(final String username, final UserProgressRequest userProgressRequest) {
////        if (user == null) {
////            return;
////        }
////        user.addUserProgress(userProgress);
////        this.userProgressRepository.save(userProgress);
////        this.userService.saveUser(user);
//        final User user = userService.getUser(username);
//        if (user == null) {
//            return;
//        }
//
//        final UserProgress userProgress = new UserProgress(userProgressRequest);
//        this.userProgressRepository.save(userProgress);
//
//        user.addUserProgress(userProgress);
//        userService.saveUser(user);
//    }

    public ResponseEntity<?> addUserProgress(final String username, final UserProgressRequest userProgressRequest) {
        final User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        final UserProgress userProgress = new UserProgress(userProgressRequest.getNewWeight());
        this.userProgressRepository.save(userProgress);

        user.addUserProgress(userProgress);
        userService.saveUser(user);
        return ResponseEntity.noContent().build();
    }

    public List<UserProgress> getAll() {
        return this.userProgressRepository.findAll();
    }

    public List<UserProgress> getAll(PageRequest pageRequest) {
        return this.userProgressRepository.findAllBy(pageRequest);
    }

    public void delete(Long id) {
        this.userProgressRepository.deleteById(id);
    }
}
