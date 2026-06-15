package com.example.Match;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user/match")
public class UserMatchController {
    private final MatchService matchService;

    public UserMatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public Page<MatchDTO> getAllMatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return matchService.getAllMatches(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public MatchDTO getMatchById(@PathVariable Integer id){
        return matchService.getMatchDTOById(id);
    }

}
