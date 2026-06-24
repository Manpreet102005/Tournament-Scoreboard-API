package com.example.Match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Integer> {
    boolean existsByMatchTitle(String matchTitle);
    List<Match> findByMatchStatus(MatchStatus matchStatus);
    @Query("SELECT m FROM Match m ORDER BY CASE m.matchStatus WHEN 'ONGOING' THEN 1 WHEN 'SCHEDULED' THEN 2 WHEN 'COMPLETED' THEN 3 END")
    Page<Match> findAllSortedByStatus(Pageable pageable);
}
