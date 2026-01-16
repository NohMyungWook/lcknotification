package com.mw.lck_notifier.domain.match;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "match_day")
@Getter
public class MatchDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate matchDate;

    protected MatchDay() {}

    private MatchDay(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public static MatchDay of(LocalDate date) {
        return new MatchDay(date);
    }
}
