package com.example.backend.Library.service.interfaces;

public interface IEmailService {
    void sendEmail(String toEmail, String text, String subject);
}
