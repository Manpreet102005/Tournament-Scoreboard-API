package com.example.Scoreboard;

import com.example.Team.TeamDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/scoreboard")
public class ScoreboardController {
    private ScoreboardService scoreboardService;

    public ScoreboardController(ScoreboardService scoreboardService){
        this.scoreboardService=scoreboardService;
    }
    @GetMapping()
    public List<TeamDTO> showScoreboard(){
        return scoreboardService.showScoreboard();
    }

}
