package com.example.backend.Library.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface IPasswordResetService {

    void initiatePasswordReset(HttpServletRequest request, String email);

    boolean validateOTP(String email, String otp);

    Map<String, String> resetPassword(HttpServletRequest request, String email, String newPassword, String otp);

    void sendPasswordResetSuccessEmail(String email);

}
