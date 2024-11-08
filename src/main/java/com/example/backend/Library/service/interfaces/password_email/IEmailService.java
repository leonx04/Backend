package com.example.backend.Library.service.interfaces.password_email;

public interface IEmailService {
    void sendEmail(String toEmail, String text, String subject);
}
