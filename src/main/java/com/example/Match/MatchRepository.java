package com.example.Match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Integer> {
    boolean existsByMatchTitle(String matchTitle);
    List<Match> findByMatchStatus(MatchStatus matchStatus);
}
