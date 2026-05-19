package com.example.Player;

import com.example.Match.MatchRepository;
import com.example.Team.Team;
import com.example.Team.TeamRepository;
import com.example.exceptions.PlayerNotFoundException;
import com.example.exceptions.TeamNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@Validated
public class PlayerController {
    private final PlayerService service;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    PlayerController(PlayerService service, PlayerRepository playerRepository, TeamRepository teamRepository){
        this.service=service;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }
    //getAll
    @GetMapping()
    public List<Player> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Integer id){
        return service.getById(id);
    }

    @PostMapping()
    public ResponseEntity<String> addPlayer(@Valid  @RequestBody Player player){
       return service.addPlayer(player);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Integer id){
        return service.deletePlayer(id);
    }

}
