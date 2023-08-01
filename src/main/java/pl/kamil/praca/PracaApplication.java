package pl.kamil.praca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PracaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracaApplication.class, args);
    }


    @Bean
    JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
