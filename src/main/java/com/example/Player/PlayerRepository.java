package com.example.Player;

import com.example.Team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    boolean existsByPlayerName(String playerName);

    List<Player> findPlayersByTeam(Team team);
}
