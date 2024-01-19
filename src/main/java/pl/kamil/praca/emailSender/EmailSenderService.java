package pl.kamil.praca.emailSender;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;
    public void sendSimpleEmail(String from, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(body);
        message.setSubject(subject);
        message.setReplyTo(from);
        javaMailSender.send(message);
        System.out.println("Wysłano maila!");
    }
}
