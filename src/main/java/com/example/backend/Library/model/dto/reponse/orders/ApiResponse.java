package com.example.backend.Library.model.dto.reponse.orders;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;


    public ApiResponse(String message, String s) {
        this.message = message;
    }

    public String getMessage() { return message; }
}
