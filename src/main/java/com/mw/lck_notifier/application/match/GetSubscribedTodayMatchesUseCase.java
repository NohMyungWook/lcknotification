package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.api.match.TodayMatchResponse;
import com.mw.lck_notifier.application.match.utils.MatchMapper;
import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetSubscribedTodayMatchesUseCase {

    private final MatchRepository matchRepository;

    public GetSubscribedTodayMatchesUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<TodayMatchResponse> get(Long deviceId) {
        LocalDate today = LocalDate.now(); // KST

        List<Match> matches =
                matchRepository.findSubscribedMatchesByDate(deviceId, today);

        return matches.stream()
                .map(MatchMapper::toResponse)
                .toList();
    }
}
