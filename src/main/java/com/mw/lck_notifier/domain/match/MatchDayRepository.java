package com.mw.lck_notifier.domain.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MatchDayRepository extends JpaRepository<MatchDay, Long> {
    Optional<MatchDay> findByMatchDate(LocalDate date);
}