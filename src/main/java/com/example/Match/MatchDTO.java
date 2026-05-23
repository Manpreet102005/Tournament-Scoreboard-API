package com.example.Match;

import java.time.LocalDateTime;

public class MatchDTO {
    Integer matchId;
    String matchTitle;
    LocalDateTime matchDateTime;
    String teamAName;
    String teamBName;
    Integer teamAScore;
    Integer teamBScore;
    MatchStatus matchStatus;

    public MatchDTO(Integer matchId, String matchTitle, LocalDateTime matchDateTime, String teamAName, String teamBName, Integer teamAScore, Integer teamBScore, MatchStatus matchStatus) {
        this.matchId = matchId;
        this.matchTitle = matchTitle;
        this.matchDateTime = matchDateTime;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.teamAScore = teamAScore;
        this.teamBScore = teamBScore;
        this.matchStatus = matchStatus;
    }
}
