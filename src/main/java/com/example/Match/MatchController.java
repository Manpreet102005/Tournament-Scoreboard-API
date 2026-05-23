package com.example.Match;

import jakarta.validation.Valid;
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
    public List<MatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public MatchDTO getMatchById(@PathVariable Integer id){
        return matchService.getMatchDTOById(id);
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
