package pl.kamil.praca;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.kamil.praca.emailSender.EmailSenderService;

@SpringBootApplication
public class PracaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracaApplication.class, args);
    }
}
