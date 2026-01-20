package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinishMatchUseCase {

    private final MatchRepository matchRepository;

    public FinishMatchUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional
    public void finish(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found: " + matchId));

        match.finish();
    }
}
