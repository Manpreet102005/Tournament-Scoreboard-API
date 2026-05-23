package com.example.Scoreboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/scoreboard")
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
