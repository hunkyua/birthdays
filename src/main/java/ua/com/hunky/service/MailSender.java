package ua.com.hunky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String data) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(username);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(data);

        mailSender.send(message);
    }
}
