package com.example.Match;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/match")
@Validated
public class AdminMatchController {

    private final MatchService matchService;

    public AdminMatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/{teamAId}/{teamBId}")
    public ResponseEntity<String> addMatch(@Valid @RequestBody Match match,
                                           @PathVariable Integer teamAId,
                                           @PathVariable Integer teamBId){
        return matchService.addMatch(match,teamAId,teamBId);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<String> removeMatch(@PathVariable Integer matchId){
        return matchService.removeMatch(matchId);
    }

    @PutMapping("reschedule/{matchId}/{newDateTime}")
    public ResponseEntity<String> reScheduleMatch(@PathVariable Integer matchId,@PathVariable LocalDateTime newDateTime){
        return matchService.rescheduleMatch(matchId,newDateTime);
    }

    @PutMapping("/{matchId}/{teamId}/{score}")
    public ResponseEntity<String> updateTeamScore(@PathVariable Integer matchId,
                                                  @PathVariable Integer teamId,
                                                  @PathVariable Integer score){
        return matchService.updateTeamScore(matchId,teamId,score);
    }

    @PutMapping("/start/{matchId}")
    public ResponseEntity<String> startMatch(@PathVariable Integer matchId){
        return matchService.startMatch(matchId);
    }

    @PutMapping("/end/{matchId}")
    public ResponseEntity<String> endMatch(@PathVariable Integer matchId){
        return matchService.endMatch(matchId);
    }
}
