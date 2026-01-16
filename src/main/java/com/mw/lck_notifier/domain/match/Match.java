package com.mw.lck_notifier.domain.match;

import com.mw.lck_notifier.domain.team.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "match")
@Getter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private MatchDay matchDay;
    @ManyToOne(optional = false) private Team teamA;
    @ManyToOne(optional = false) private Team teamB;

    private int bestOf;
    private int matchOrder;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    protected Match() {}

    public static Match schedule(
            MatchDay day,
            Team a,
            Team b,
            int bestOf,
            int order,
            LocalDateTime startTimeUtc
    ) {
        Match m = new Match();
        m.matchDay = day;
        m.teamA = a;
        m.teamB = b;
        m.bestOf = bestOf;
        m.matchOrder = order;
        m.startTime = startTimeUtc;
        m.status = MatchStatus.SCHEDULED;
        return m;
    }

    public void start() {
        this.status = MatchStatus.IN_PROGRESS;
    }

}
