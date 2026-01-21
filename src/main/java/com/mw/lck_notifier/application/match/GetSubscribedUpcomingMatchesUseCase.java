package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.api.match.TodayMatchResponse;
import com.mw.lck_notifier.application.match.utils.MatchMapper;
import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetSubscribedUpcomingMatchesUseCase {

    private final MatchRepository matchRepository;

    public GetSubscribedUpcomingMatchesUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<TodayMatchResponse> get(Long deviceId) {
        LocalDateTime now = LocalDateTime.now(); // KST

        List<Match> matches =
                matchRepository.findSubscribedUpcomingMatches(deviceId, now);

        return matches.stream()
                .map(MatchMapper::toResponse)
                .toList();
    }
}
