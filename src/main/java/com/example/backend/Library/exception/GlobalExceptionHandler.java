package com.example.backend.Library.exception;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Nguyên nhân : tham số đầu vào ko qua đc xác thực bằng annotation (@Valid hoặc @Validated).
    @ExceptionHandler({MethodArgumentNotValidException.class})//Invalid input data.
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorReponse handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.info("============== handleValidationException ==============");
        String errorMessage = ExceptionMessageFormatter.formatValidationMessage(ex);
        ErrorReponse errorReponse = createErrorReponse(ex, request);
        errorReponse.setMessage(errorMessage);
        return errorReponse;
    }

    // Nguyên nhân : Tham số đầu vào p.thức (@PathVariable hoặc @RequestParam hoặc thuộc tính Bean) vi phạm ràng buộc.
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorReponse handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.info("============== handleConstraintViolationException ==============");
        String errorMessage = ExceptionMessageFormatter.formatConstraintViolationMessage(ex);

        ErrorReponse errorReponse = createErrorReponse(ex, request);
        errorReponse.setMessage(errorMessage);

        return errorReponse;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorReponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        log.info("============== handleMissingServletRequestParameterException ==============");
        return createErrorReponse(ex, request);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorReponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.info("============== handleResourceNotFoundException ==============");
        return createErrorReponse(ex, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorReponse handleException(Exception ex, WebRequest request) {
        log.info("============== handleException ==============");
        return createErrorReponse(ex, request);
    }


    //HttpMessageNotReadableException
    //InternalAuthenticationServiceException

    private ErrorReponse createErrorReponse(Exception ex, WebRequest request) {
        return ErrorReponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}
