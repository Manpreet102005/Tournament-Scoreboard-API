package com.example.Match;

import com.example.Team.Team;
import com.example.Team.TeamRepository;
import com.example.exceptions.MatchNotFoundException;
import com.example.exceptions.MatchScheduledInPastException;
import com.example.exceptions.MatchTitleAlreadyExists;
import com.example.exceptions.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
@Service
public class MatchService {

    private MatchRepository matchRepository;
    private TeamRepository teamRepository;

    public MatchService(MatchRepository matchRepository) {
        this.teamRepository=teamRepository;
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getById(Integer id) {
        return matchRepository.findById(id).orElseThrow(()->new MatchNotFoundException(id));
    }

    public ResponseEntity<String> addMatch(Match match, Integer teamAId, Integer teamBId){
        if(matchRepository.existsByMatchTitle(match.getMatchTitle())){
            throw new MatchTitleAlreadyExists(match.getMatchTitle());
        }
        Team teamA=teamRepository.findById(teamAId).orElseThrow(()->
                new TeamNotFoundException(teamAId)
        );
        Team teamB=teamRepository.findById(teamBId).orElseThrow(()->
                new TeamNotFoundException(teamBId)
        );

        match.setTeamA(teamA);
        match.setTeamB(teamB);
        matchRepository.save(match);
        return ResponseEntity.ok().body("Match Added Successfully. Assigned Match Id: "+match.getMatchId());
    }

    public ResponseEntity<String> removeMatch(Integer id){
        if(!matchRepository.existsById(id)){
            throw new MatchNotFoundException(id);
        }
        matchRepository.deleteById(id);
        return ResponseEntity.ok().body("Removed Successfully");
    }

    public ResponseEntity<String> rescheduleMatch(Integer matchId, LocalDateTime newDateTime) {
        Match match=matchRepository.findById(matchId).orElseThrow(()->
                new MatchNotFoundException(matchId));

        if(newDateTime.isBefore(LocalDateTime.now())){
            throw new MatchScheduledInPastException();
        }
        match.setMatchDateTime(newDateTime);
        matchRepository.save(match);
        return ResponseEntity.ok().body("Match Rescheduled Successfully.");
    }
}
