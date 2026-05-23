package com.example.Scoreboard;

import com.example.Match.MatchRepository;
import com.example.Team.Team;
import com.example.Team.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreboardService {
    private final TeamRepository teamRepository;

    public ScoreboardService(TeamRepository teamRepository) {
        this.teamRepository=teamRepository;
    }

    public List<TeamDTO> showScoreboard() {
        List<TeamDTO> scoreboardList=new ArrayList<>();
        List<Team> teamList=teamRepository.findAll();
        teamList.sort(
                (a,b)->Integer.compare(b.getTotalScore(),a.getTotalScore())
        );

        for(int i=0;i<teamList.size();i++){
            Team team=teamList.get(i);
            Double winRatio= team.getMatchesPlayed()==0?0:(double)team.getWins()/team.getMatchesPlayed()*100;
            scoreboardList.add(
                    new TeamDTO(i+1,
                            team.getTeamId(),
                            team.getTeamName(),
                            team.getTotalScore(),
                            team.getMatchesPlayed(),
                            team.getWins(),
                            winRatio
                    )
            );
        }
        return scoreboardList;
    }

}
