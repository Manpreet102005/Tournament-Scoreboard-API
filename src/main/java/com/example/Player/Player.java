package com.example.Player;

import com.example.Team.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="Name of player can't be blank")
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
    public Player(){};
    public Player(String name, Integer score,Team team) {
        this.name = name;
        this.team=team;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
