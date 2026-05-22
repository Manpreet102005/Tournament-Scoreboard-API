package com.example.exceptions;

public class InvalidScoreException extends RuntimeException {
    public InvalidScoreException(){
        super("Score must be between 0 and 100");
    }
}
