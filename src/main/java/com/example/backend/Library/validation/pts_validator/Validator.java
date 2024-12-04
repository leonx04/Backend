/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.validation.pts_validator;

import org.springframework.validation.BindingResult;

import java.util.Map;

public class Validator {
    public static Map errorField(Map response, BindingResult result) {
        if (result.hasErrors()) {
            result.getFieldErrors().stream().forEach(e -> {
                response.put(e.getField(), e.getDefaultMessage());
            });
            response.put("status", 400);
        } else {
            response.put("status", 200);
        }
        return response;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }


}
