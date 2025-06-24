package com.hadyan.tracknumbergenerator.exception;

import com.hadyan.tracknumbergenerator.dto.ApiRespDto;
import com.hadyan.tracknumbergenerator.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiRespDto<?>> handleValidationErrors(BindException e) {
        return ResponseUtil.failWithErrors(HttpStatus.BAD_REQUEST, "Validation failed", e.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseUtil.failWithMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
