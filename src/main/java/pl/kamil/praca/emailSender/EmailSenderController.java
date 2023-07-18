package pl.kamil.praca.emailSender;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailSenderController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public ResponseEntity<?>sendEmail(Authentication authentication, @RequestBody EmailRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body("Użytkownik nie jest zautoryzowany!");
        }
        this.emailSenderService.sendSimpleEmail(request.getFrom(), request.getSubject(), request.getBody());
        return ResponseEntity.noContent().build();
    }
}
