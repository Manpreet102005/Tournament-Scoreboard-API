package com.example.Match;

import com.example.Team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;

    @NotBlank(message = "Match title cant be blank")
    @Column(unique=true)
    private String matchTitle;

    @ManyToOne
    @JoinColumn(name="team_a_id")
    private Team teamA;

    @ManyToOne
    @JoinColumn(name="team_b_id")
    private Team teamB;

    Match(){}
    Match(String matchTitle, Team teamA, Team teamB){
        this.matchTitle=matchTitle;
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
}
