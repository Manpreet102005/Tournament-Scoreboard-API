package com.example.Player;

import com.example.Team.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/admin/player")
@Validated
public class AdminPlayerController {
    private final PlayerService playerService;


    public AdminPlayerController(PlayerService playerService){
        this.playerService=playerService;
    }

    @PostMapping()
    public ResponseEntity<String> addPlayer(@Valid  @RequestBody Player player){
       return playerService.addPlayer(player);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Integer id){
        return playerService.deletePlayer(id);
    }

}
