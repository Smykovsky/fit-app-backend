package pl.kamil.praca.emailSender;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    public void sendSimpleEmail(String from, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("ksmyk.dev2000@gmail.com");
        message.setText(body);
        message.setSubject(subject);
        message.setReplyTo(from);
        javaMailSender.send(message);
        System.out.println("Wysłano maila!");
    }
}
