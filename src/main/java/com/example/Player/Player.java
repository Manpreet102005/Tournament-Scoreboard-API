package com.example.Player;

import com.example.Team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @NotBlank(message="Name of player can't be blank")
    @Column(unique = true)
    private String playerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;
    public Player(){};
    public Player(String playerName){
        this.playerName=playerName;
    }


    public Integer getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
