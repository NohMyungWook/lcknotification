package com.mw.lck_notifier.domain.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByStatusAndStartTimeLessThanEqual(MatchStatus status, LocalDateTime now);
}
