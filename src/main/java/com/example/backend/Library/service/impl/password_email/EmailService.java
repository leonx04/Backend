package com.example.backend.Library.service.impl.password_email;

import com.example.backend.Library.service.interfaces.password_email.IEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private LocalDate getSentDate() {
        return LocalDate.now();
    }

    @Override
    public void sendEmail(String toEmail, String text, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${spring.mail.username}");
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);
        // Đặt ngày gửi
        message.setSentDate(Date.valueOf(getSentDate()));
        mailSender.send(message);
    }
}