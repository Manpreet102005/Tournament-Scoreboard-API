package com.example;

import com.example.exceptions.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UniversalController {
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handle(PlayerNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(PlayerNameAlreadyExists.class)
    public ResponseEntity<String> handle(PlayerNameAlreadyExists e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validateHandle(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(e.getBindingResult().
                getFieldErrors().
                get(0).
                getDefaultMessage());
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<String> handle(MatchNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MatchTitleAlreadyExists.class)
    public ResponseEntity<String> handle(MatchTitleAlreadyExists e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<String> handle(TeamNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(TeamNameAlreadyExist.class)
    public ResponseEntity<String> handle(TeamNameAlreadyExist e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(MatchScheduledInPastException.class)
    public ResponseEntity<String> handle(MatchScheduledInPastException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(TeamNotPartOfMatchException.class)
    public ResponseEntity<String> handle(TeamNotPartOfMatchException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
