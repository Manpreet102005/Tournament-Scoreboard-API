package com.example.Team;

import com.example.Player.Player;
import com.example.Player.PlayerDTO;
import com.example.Player.PlayerService;
import com.example.Player.exceptions.PlayerNotFoundException;
import com.example.Player.PlayerRepository;
import com.example.Team.exceptions.TeamAlreadyAssignedException;
import com.example.Team.exceptions.TeamNameAlreadyExist;
import com.example.Team.exceptions.TeamNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    public TeamService(TeamRepository matchRepository, PlayerRepository playerRepository, PlayerService playerService) {
        this.teamRepository = matchRepository;
        this.playerRepository=playerRepository;
        this.playerService = playerService;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Integer id) {
        return teamRepository.findById(id).orElseThrow(()->new TeamNotFoundException(id));
    }

    public ResponseEntity<String> addTeam(Team team){
        if(teamRepository.existsByTeamName(team.getTeamName())){
            throw new TeamNameAlreadyExist(team.getTeamName());
        }
        teamRepository.save(team);
        return ResponseEntity.ok().body("Team with id: "+team.getTeamId()+" Added Successfully");
    }

    public ResponseEntity<String> removeTeam(Integer id){
        if(!teamRepository.existsById(id)){
            throw new TeamNotFoundException(id);
        }
        Team team=getTeamById(id);
        List<Player> players = team.getPlayers();
        for(Player player : players) {
            player.setTeam(null);
            playerRepository.save(player);
        }
        teamRepository.deleteById(id);
        return ResponseEntity.ok().body("Team with id: "+id+" Removed Successfully");
    }

    public ResponseEntity<String> assignPlayerToTeam(Integer teamId, Integer playerId) {
        Team team=teamRepository.findById(teamId).orElseThrow(()->
                new TeamNotFoundException(teamId));
        Player player=playerRepository.findById(playerId).orElseThrow(()->
                new PlayerNotFoundException(playerId));
        if(player.getTeam()!=null){
            throw new TeamAlreadyAssignedException(playerId,player.getTeam().getTeamId());
        }
        player.setTeam(team);
        playerRepository.save(player);
        return ResponseEntity.ok().body("Player with id: "+player.getPlayerId()+" added to team with id: "+teamId+" successfully.");
    }

    public ResponseEntity<String> renameTeam(Integer teamId, String newTeamName) {
        Team team=teamRepository.findById(teamId).orElseThrow(()->
                new TeamNotFoundException(teamId));
        String oldName=team.getTeamName();
        team.setTeamName(newTeamName);
        teamRepository.save(team);
        return ResponseEntity.ok().body("Team with id: "+teamId+" renamed from "+oldName+" to "+newTeamName+" successfully.");
    }

    public List<PlayerDTO> getPlayersByTeam(Integer teamId) {
        List<Player> playersByTeam=playerRepository.findPlayersByTeam(getTeamById(teamId));
        List<PlayerDTO> playersDTOList=new ArrayList<>();
        for(Player player :playersByTeam){
            playersDTOList.add(playerService.toPlayerDTO(player));
        }
        return playersDTOList;
    }
}
