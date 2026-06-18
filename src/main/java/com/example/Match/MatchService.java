package com.example.Match;

import com.example.Match.exceptions.*;
import com.example.Team.Team;
import com.example.Team.exceptions.TeamNotFoundException;
import com.example.Team.exceptions.TeamNotPartOfMatchException;
import com.example.Team.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
@Service
public class MatchService {

    private MatchRepository matchRepository;
    private TeamRepository teamRepository;

    public MatchService(MatchRepository matchRepository,TeamRepository teamRepository) {
        this.teamRepository=teamRepository;
        this.matchRepository = matchRepository;
    }

    public Page<MatchDTO> getAllMatches(Pageable pageable) {
        return matchRepository.findAll(pageable).map(this::toMatchDTO);
    }

    public MatchDTO getMatchDTOById(Integer id) {
        Match match=matchRepository.findById(id).orElseThrow(()->new MatchNotFoundException(id));
        return new MatchDTO(
                match.getMatchId(),
                match.getMatchTitle(),
                match.getMatchDateTime(),
                match.getTeamAName(),
                match.getTeamBName(),
                match.getTeamAScore(),
                match.getTeamBScore(),
                match.getMatchStatus()
        );
    }
    private Match getMatchById(Integer id){
        return matchRepository.findById(id).orElseThrow(()->new MatchNotFoundException(id));
    }

    public ResponseEntity<String> addMatch(Match match, Integer teamAId, Integer teamBId){
        if(match.getMatchDateTime().isBefore(LocalDateTime.now())){
            throw new MatchScheduledInPastException();
        }
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
        match.setTeamAName(teamA.getTeamName());
        match.setTeamBName(teamB.getTeamName());
        matchRepository.save(match);
        return ResponseEntity.ok().body("Match Added Successfully. Assigned Match Id: "+match.getMatchId());
    }

    public ResponseEntity<String> removeMatch(Integer id){
        Match match =getMatchById(id);
        if(!match.getMatchStatus().equals(MatchStatus.SCHEDULED)){
            throw new MatchStatusException("ONGOING/COMPLETED matches can not be deleted. Current Status: "+match.getMatchStatus());
        }
        matchRepository.deleteById(id);
        return ResponseEntity.ok().body("Match with id: "+id+" Removed Successfully");
    }

    public ResponseEntity<String> rescheduleMatch(Integer matchId, LocalDateTime newDateTime) {
        if(newDateTime.isBefore(LocalDateTime.now())){
            throw new MatchScheduledInPastException();
        }
        Match match=getMatchById(matchId);
        if(!match.getMatchStatus().equals(MatchStatus.SCHEDULED)){
            throw new MatchStatusException("ONGOING/COMPLETED matches can not be rescheduled. Current Status: "+match.getMatchStatus());

        }
        match.setMatchDateTime(newDateTime);
        matchRepository.save(match);
        return ResponseEntity.ok().body("Match Rescheduled Successfully. New Time: "+newDateTime);
    }

    public ResponseEntity<String> updateTeamScore(Integer matchId, Integer teamId, Integer score) {
        Match match=getMatchById(matchId);
        if (!match.getMatchStatus().equals(MatchStatus.ONGOING)) {
            throw new MatchStatusException("Match must be ONGOING to update scores. Current status: " + match.getMatchStatus());
        }
        if(score<0 || score>100){
            throw new InvalidScoreException();
        }
        if(match.getTeamA().getTeamId().equals(teamId)){
            match.setTeamAScore(score);
        }
        else if(match.getTeamB().getTeamId().equals(teamId)){
            match.setTeamBScore(score);
        }
        else {
            throw new TeamNotPartOfMatchException(teamId,matchId);
        }
        matchRepository.save(match);
        return ResponseEntity.ok().body("Team Score Updated Successfully");
    }

    public ResponseEntity<String> startMatch(Integer matchId) {
        Match match=getMatchById(matchId);
        if(match.getMatchStatus().equals(MatchStatus.ONGOING)){
            throw new MatchStatusException("Match Already ONGOING");
        }
        if(match.getMatchStatus().equals(MatchStatus.COMPLETED)){
            throw new MatchStatusException("Can not update scores of COMPLETED match");
        }
        match.setMatchStatus(MatchStatus.ONGOING);
        matchRepository.save(match);
        return ResponseEntity.ok().body("Match with id: "+matchId+" is now ONGOING.");
    }
    @Transactional
    public ResponseEntity<String> endMatch(Integer matchId) {
        Match match = getMatchById(matchId);
        if (!match.getMatchStatus().equals(MatchStatus.ONGOING)) {
            throw new MatchStatusException("Match must be ONGOING to end it. Current status: " + match.getMatchStatus());
        }
        Team teamA=match.getTeamA();
        Team teamB=match.getTeamB();

        teamA.setTotalScore(teamA.getTotalScore()+match.getTeamAScore());
        teamB.setTotalScore(teamB.getTotalScore()+match.getTeamBScore());
        teamA.setMatchesPlayed(teamA.getMatchesPlayed()+1);
        teamB.setMatchesPlayed(teamB.getMatchesPlayed()+1);

        if(match.getTeamAScore()>match.getTeamBScore()){
            teamA.setWins(teamA.getWins()+1);
        }
        else if(match.getTeamAScore()<match.getTeamBScore()){
            teamB.setWins(teamB.getWins()+1);
        }
        else{
            teamA.setDraws(teamA.getDraws()+1);
            teamB.setDraws(teamB.getDraws()+1);
        }

        match.setMatchStatus(MatchStatus.COMPLETED);

        teamRepository.saveAll(List.of(teamA,teamB));
        matchRepository.save(match);
        return ResponseEntity.ok().body("Match with id: "+matchId+" is now COMPLETED ");
    }

    public List<MatchDTO> getMatchesByStatus(String matchStatus){
        MatchStatus status = MatchStatus.valueOf(matchStatus);
        return matchRepository.findByMatchStatus(status).stream().map(this::toMatchDTO).toList();
    }

    private MatchDTO toMatchDTO(Match match) {
        return new MatchDTO(
                match.getMatchId(),
                match.getMatchTitle(),
                match.getMatchDateTime(),
                match.getTeamAName(),
                match.getTeamBName(),
                match.getTeamAScore(),
                match.getTeamBScore(),
                match.getMatchStatus()
        );
    }
}

