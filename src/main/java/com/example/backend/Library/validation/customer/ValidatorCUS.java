/*
 * Author: Phạm Thái Sơn || JavaDEV
 * Facebook:https://www.facebook.com/son4k2
 * Github: https://github.com/SONPC-Developer
 * Youtube: https://www.youtube.com
 */

package com.example.backend.Library.validation.customer;

import org.springframework.validation.BindingResult;

import java.util.Map;

public class ValidatorCUS {
    public static Map errorCUS(Map response, BindingResult result, int status) {
        if (result.hasErrors()) {
            result.getFieldErrors().stream().forEach(e -> {
                response.put(e.getField(), e.getDefaultMessage());
            });
            response.put("status", status);
            return response;
        }
        return null;
    }
}
