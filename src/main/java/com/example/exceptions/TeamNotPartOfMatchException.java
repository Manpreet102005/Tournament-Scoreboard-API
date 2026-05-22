package com.example.exceptions;

public class TeamNotPartOfMatchException extends RuntimeException {
    public TeamNotPartOfMatchException(Integer teamId, Integer matchId) {
        super("Team with id: "+teamId+" is not a part of match with id: "+matchId);
    }
}
