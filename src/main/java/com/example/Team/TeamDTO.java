package com.example.Team;

public class TeamDTO {
    private Integer rank;
    private Integer teamId;
    private String teamName;
    private Integer totalScore;
    private Integer matchesPlayed;
    private Integer wins;
    private Double winRatio;

    public TeamDTO(Integer rank, Integer teamId, String teamName, Integer totalScore, Integer matchesPlayed, Integer wins, Double winRatio) {
        this.rank=rank;
        this.teamId = teamId;
        this.teamName = teamName;
        this.totalScore = totalScore;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.winRatio=winRatio;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public Integer getWins() {
        return wins;
    }

    public Double getWinRatio() {
        return winRatio;
    }
}
