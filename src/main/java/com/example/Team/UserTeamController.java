package com.example.Team;

import com.example.Player.PlayerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/user/team")
public class UserTeamController {
    private final TeamService teamService;

    public UserTeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Integer id){
        return teamService.getTeamById(id);
    }

    @GetMapping()
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/{teamId}/players")
    public List<PlayerDTO> getPlayersByTeam(@PathVariable Integer teamId){
        return teamService.getPlayersByTeam(teamId);
    }
}
