package com.abhijit.accountsvc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception){
        StringBuilder sb=new StringBuilder();
        sb.append("Exception in processing.Exception"+exception.getClass().getSimpleName());
        exception.getBindingResult().getFieldErrors().forEach(
                ex->{
                    sb.append("field:");
                    sb.append(ex.getField());
                    sb.append(" msg: ");
                    sb.append(ex.getDefaultMessage());
                    sb.append(" ");
                    sb.append(System.lineSeparator());
                }
        );
        log.error(sb.toString());
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(value=AppAccountNotFoundException.class)
    public ResponseEntity<String> handleException(AppAccountNotFoundException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
