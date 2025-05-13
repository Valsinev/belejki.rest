package com.belejki.belejki.restful.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendConfirmationEmail(String email, String token);
}

