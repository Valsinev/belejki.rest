package com.belejki.belejki.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost}") // Default to localhost if not set
    private String baseUrl;

    @Value("${server.port:8080}") // Default to 8080 if not set
    private String serverPort;

    @Autowired
    private final MessageSource messageSource;

    public EmailServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public void sendConfirmationEmail(String email, String token, Locale locale) {
        String confirmationLink = baseUrl.concat(":").concat(serverPort).concat("/confirm?token=").concat(token);
        String subject = messageSource.getMessage("email.confirm.subject", null, locale);
        String message = messageSource.getMessage("email.confirm.body", new Object[]{confirmationLink}, locale);

        // Send the email (implementation depends on your email sending logic)
        sendEmail(email, subject, message);
    }
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

}

