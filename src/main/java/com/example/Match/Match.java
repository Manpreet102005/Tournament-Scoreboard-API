package com.example.Match;

import com.example.Team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;

    @NotBlank(message = "Match title cant be blank")
    @Column(unique=true)
    private String matchTitle;

    @NotNull(message="Match date can't be null")
    private LocalDateTime matchDateTime;

    @ManyToOne
    @JoinColumn(name="team_a_id")
    private Team teamA;

    @ManyToOne
    @JoinColumn(name="team_b_id")
    private Team teamB;

    private Integer teamAScore=0;
    private Integer teamBScore=0;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus=MatchStatus.SCHEDULED;

    public Match(){}
    public Match(String matchTitle, Team teamA, Team teamB, LocalDateTime matchDateTime){
        this.matchTitle=matchTitle;
        this.matchDateTime=matchDateTime;
        this.teamA=teamA;
        this.teamB=teamB;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(Integer teamAScore) {
        this.teamAScore = teamAScore;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(Integer teamBScore) {
        this.teamBScore = teamBScore;
    }

    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }

    public void setMatchDateTime(LocalDateTime matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }
}
