package com.example.backend.Library.model.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class ResponseData<T> implements Serializable {

    private final int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T date;

    public ResponseData(int status, String message, T date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }

    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
