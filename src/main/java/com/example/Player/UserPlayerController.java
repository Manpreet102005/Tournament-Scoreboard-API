package com.example.Player;

import com.example.Team.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/player")
public class UserPlayerController {
    private final PlayerService playerService;

    public UserPlayerController(PlayerService playerService, PlayerRepository playerRepository, TeamRepository teamRepository){
        this.playerService=playerService;
    }
    //getAll
    @GetMapping()
    public Page<Player> getAllPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return playerService.getAllPlayers(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable Integer id){
        return playerService.getById(id);
    }

}
