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

    public Integer getMatchId() {
        return matchId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }

    public String getTeamAName() {
        return teamAName;
    }

    public String getTeamBName() {
        return teamBName;
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }
}
