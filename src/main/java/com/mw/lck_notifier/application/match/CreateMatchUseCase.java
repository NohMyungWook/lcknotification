package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchDay;
import com.mw.lck_notifier.domain.match.MatchRepository;
import com.mw.lck_notifier.domain.match.MatchSet;
import com.mw.lck_notifier.domain.match.MatchSetRepository;
import com.mw.lck_notifier.domain.match.MatchDayRepository;
import com.mw.lck_notifier.domain.team.Team;
import com.mw.lck_notifier.domain.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Service
public class CreateMatchUseCase {

    private final MatchDayRepository matchDayRepository;
    private final MatchRepository matchRepository;
    private final MatchSetRepository matchSetRepository;
    private final TeamRepository teamRepository;

    public CreateMatchUseCase(
            MatchDayRepository matchDayRepository,
            MatchRepository matchRepository,
            MatchSetRepository matchSetRepository,
            TeamRepository teamRepository
    ) {
        this.matchDayRepository = matchDayRepository;
        this.matchRepository = matchRepository;
        this.matchSetRepository = matchSetRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Match create(
            LocalDateTime startTimeKst,
            Long teamAId,
            Long teamBId,
            int order,
            int bestOf
    ) {
        if (teamAId.equals(teamBId)) {
            throw new IllegalArgumentException("Teams must be different");
        }

        // 1. ID -> Entity 로드 (절대 생략 안 함)
        Team teamA = teamRepository.findById(teamAId)
                .orElseThrow(() -> new IllegalArgumentException("Team A not found: " + teamAId));

        Team teamB = teamRepository.findById(teamBId)
                .orElseThrow(() -> new IllegalArgumentException("Team B not found: " + teamBId));

        // 2. startTime에서 날짜 추출하여 MatchDay 결정
        LocalDate date = startTimeKst.toLocalDate();

        MatchDay matchDay = matchDayRepository.findByMatchDate(date)
                .orElseGet(() -> matchDayRepository.save(MatchDay.of(date)));

        // 3. Match 생성 (Team 엔티티를 그대로 넘김)
        Match match = Match.schedule(
                matchDay,
                teamA,
                teamB,
                bestOf,
                order,
                startTimeKst
        );

        matchRepository.save(match);

        // 5. BO3/BO5이므로 set 수만큼 미리 생성
        IntStream.rangeClosed(1, bestOf)
                .forEach(setNum -> {
                    MatchSet set = MatchSet.waiting(match, setNum);
                    matchSetRepository.save(set);
                });

        return match;
    }
}
