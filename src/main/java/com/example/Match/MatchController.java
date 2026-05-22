package com.example.Match;

import com.example.Team.Team;
import com.example.exceptions.MatchNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/match")
@Validated
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<Match> getAllMatches(){
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable Integer id){
        return matchService.getById(id);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<String> removeMatch(@PathVariable Integer matchId){
        return matchService.removeMatch(matchId);
    }

    @PostMapping("/{teamAId}/{teamBId}")
    public ResponseEntity<String> addMatch(@Valid @RequestBody Match match,
                                           @PathVariable Integer teamAId,
                                           @PathVariable Integer teamBId){
        return matchService.addMatch(match,teamAId,teamBId);
    }

    @PostMapping("/{matchId}/{newDateTime}")
    public ResponseEntity<String> reScheduleMatch(@PathVariable Integer matchId,@PathVariable LocalDateTime newDateTime){
        return matchService.rescheduleMatch(matchId,newDateTime);
    }

    @PostMapping("/{matchId}/{teamId}/{score}")
    public ResponseEntity<String> updateTeamScore(@PathVariable Integer matchId,
                                                  @PathVariable Integer teamId,
                                                  @PathVariable Integer score){
        return matchService.updateTeamScore(matchId,teamId,score);
    }



}
