package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.api.match.TodayMatchResponse;
import com.mw.lck_notifier.application.match.utils.MatchMapper;
import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchDay;
import com.mw.lck_notifier.domain.match.MatchDayRepository;
import com.mw.lck_notifier.domain.match.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetTodayMatchesUseCase {

    private final MatchDayRepository matchDayRepository;
    private final MatchRepository matchRepository;

    public GetTodayMatchesUseCase(
            MatchDayRepository matchDayRepository,
            MatchRepository matchRepository
    ) {
        this.matchDayRepository = matchDayRepository;
        this.matchRepository = matchRepository;
    }

    public List<TodayMatchResponse> getToday() {
        LocalDate today = LocalDate.now();   // KST 기준

        MatchDay matchDay = matchDayRepository.findByMatchDate(today)
                .orElse(null);

        if (matchDay == null) {
            return List.of();
        }

        List<Match> matches = matchRepository.findByMatchDay(matchDay);

        return matches.stream()
                .map(MatchMapper::toResponse)
                .toList();
    }
}
