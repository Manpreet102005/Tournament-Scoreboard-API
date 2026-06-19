package com.example;

import com.example.Match.exceptions.*;
import com.example.Player.exceptions.PlayerNameAlreadyExists;
import com.example.Player.exceptions.PlayerNotFoundException;
import com.example.Team.exceptions.TeamAlreadyAssignedException;
import com.example.Team.exceptions.TeamNameAlreadyExist;
import com.example.Team.exceptions.TeamNotFoundException;
import com.example.Team.exceptions.TeamNotPartOfMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class UniversalHandler {
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

    @ExceptionHandler(InvalidScoreException.class)
    public ResponseEntity<String> handle(InvalidScoreException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TeamAlreadyAssignedException.class)
    public ResponseEntity<String> handle(TeamAlreadyAssignedException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MatchStatusException.class)
    public ResponseEntity<String> handle(MatchStatusException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handle(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body("Invalid parameter type: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e){
        return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
    }
}
