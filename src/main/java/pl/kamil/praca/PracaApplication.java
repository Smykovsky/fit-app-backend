package pl.kamil.praca;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.kamil.praca.emailSender.EmailSenderService;

@SpringBootApplication
@AllArgsConstructor
public class PracaApplication {
    private EmailSenderService emailSenderService;
    public static void main(String[] args) {
        SpringApplication.run(PracaApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail() throws MessagingException {
        emailSenderService.sendSimpleEmail("kamil.smyk00@gmail.com",
                "this is email body",
                "this is email subject"
        );
    }
}
