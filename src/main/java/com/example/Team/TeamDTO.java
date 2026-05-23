package com.example.Team;

class TeamDTO {
    private Integer rank;
    private Integer teamId;
    private String teamName;
    private Integer totalScore;
    private Integer matchesPlayed;
    private Integer wins;
    private Double winRatio;

    public TeamDTO(Integer rank, Integer teamId, String teamName, Integer totalScore, Integer matchesPlayed, Integer wins, Double winRatio) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.totalScore = totalScore;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.winRatio=winRatio;
    }

}
