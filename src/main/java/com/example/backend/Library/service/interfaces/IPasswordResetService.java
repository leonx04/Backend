package com.example.backend.Library.service.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IPasswordResetService {
    void initiatePasswordReset(String email);

    boolean validateOTP(String email, String otp);

    Map<String, String> resetPassword(String email, String newPassword);

    void sendPasswordResetSuccessEmail(String email);
}
