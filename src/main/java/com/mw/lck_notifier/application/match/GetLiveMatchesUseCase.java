package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.api.match.TodayMatchResponse;
import com.mw.lck_notifier.application.match.utils.MatchMapper;
import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchRepository;
import com.mw.lck_notifier.domain.match.MatchStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetLiveMatchesUseCase {

    private final MatchRepository matchRepository;

    public GetLiveMatchesUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<TodayMatchResponse> getLive() {
        List<Match> matches = matchRepository.findByStatus(MatchStatus.IN_PROGRESS);

        return matches.stream()
                .map(MatchMapper::toResponse)
                .toList();
    }
}
