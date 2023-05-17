package pl.kamil.praca.emailSender;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailRequest {
    private String from;
    private String subject;
    private String body;
}
