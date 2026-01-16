package com.mw.lck_notifier.domain.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByStatusAndStartTimeLessThanEqual(MatchStatus status, LocalDateTime now);

    List<Match> findByMatchDay(MatchDay matchDay);

    List<Match> findByStatus(MatchStatus status);

    @Query("select m from Match m where m.startTime > :now order by m.startTime asc")
    List<Match> findUpcoming(LocalDateTime now);

    @Query("select m from Match m where m.startTime < :now and m.status = 'SCHEDULED'")
    List<Match> findTodayRemaining(LocalDateTime now);


}
