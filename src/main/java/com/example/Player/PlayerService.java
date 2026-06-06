package com.example.Player;


import com.example.Player.exceptions.PlayerNameAlreadyExists;
import com.example.Player.exceptions.PlayerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDTO getDTOById(Integer id) {
        Player player=playerRepository.findById(id).orElseThrow(()->new PlayerNotFoundException(id));
        return toPlayerDTO(player);
    }

    public Page<PlayerDTO> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable).map(this::toPlayerDTO);
    }

    public ResponseEntity<String> addPlayer(Player player) {
        if(playerRepository.existsByPlayerName(player.getPlayerName())){
            throw new PlayerNameAlreadyExists(player.getPlayerName());
        }
        playerRepository.save(player);
        return ResponseEntity.ok().body("Player added successfully. Assigned Id: "+player.getPlayerId());
    }

    public ResponseEntity<String> deletePlayer(Integer id) {
        if(!playerRepository.existsById(id)){
            throw new PlayerNotFoundException(id);
        }
        playerRepository.deleteById(id);
        return ResponseEntity.ok().body("Player with id: "+id+" deleted successfully");
    }
    public PlayerDTO toPlayerDTO(Player player){
        return new PlayerDTO(
                player.getPlayerId(),
                player.getPlayerName(),
                player.getTeam().getTeamName()
        );
    }
}
