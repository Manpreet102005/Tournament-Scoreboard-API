package com.example.Player;


import com.example.exceptions.PlayerNameAlreadyExists;
import com.example.exceptions.PlayerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository repository) {
        this.playerRepository = playerRepository;
        this.teamRepository= teamRepository;
    }

    public Player getById(Integer id) {
        return playerRepository.findById(id).orElseThrow(()->new PlayerNotFoundException(id));
    }

    public List<Player> getAll() {
        List<Player> list = playerRepository.findAll();
        return list;
    }

    public ResponseEntity<String> addPlayer(Player player) {
        if(playerRepository.existsByName(player.getName())){
            throw new PlayerNameAlreadyExists(player.getName());
        }
        playerRepository.save(player);
        return ResponseEntity.ok().body("Player added successfully. Assigned Id: "+player.getId());
    }

    public ResponseEntity<String> deletePlayer(Integer id) {
        if(!playerRepository.existsById(id)){
            throw new PlayerNotFoundException(id);
        }
        playerRepository.deleteById(id);
        updateRank();
        return ResponseEntity.ok().body("Player with id: "+id+" deleted successfully");
    }

}
