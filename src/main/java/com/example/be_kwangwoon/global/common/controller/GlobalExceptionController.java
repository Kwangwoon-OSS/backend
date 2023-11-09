package com.example.be_kwangwoon.global.common.controller;


import com.example.be_kwangwoon.global.common.controller.response.FieldErrorResponse;
import com.example.be_kwangwoon.global.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> exceptionHandler(CustomException exception) {
        log.warn("CustomException = {}", exception);
        return ResponseEntity.status(exception.getExceptionCode().getStatus()).body(exception.getExceptionCode());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validation(MethodArgumentNotValidException exception) {
        log.warn("MethodArgumentNotValidException = {}", exception);
        FieldErrorResponse fieldValidation = new FieldErrorResponse(exception);
        return ResponseEntity.badRequest().body(fieldValidation.getResponse());
    }
}

