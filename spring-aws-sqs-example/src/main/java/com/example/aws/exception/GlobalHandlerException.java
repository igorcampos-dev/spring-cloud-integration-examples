package com.example.aws.exception;

import com.example.aws.exception.response.Error;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<@NonNull Error> handleRuntimeException(RuntimeException ex, HttpServletRequest s) {
        log.error("BusinessException= {}", ex.getMessage());
        return Error.response(ex.getMessage(), HttpStatus.BAD_REQUEST, s);
    }

}