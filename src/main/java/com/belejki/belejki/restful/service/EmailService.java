package com.belejki.belejki.restful.service;

import java.util.Locale;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendConfirmationEmail(String email, String token, Locale locale);
}

