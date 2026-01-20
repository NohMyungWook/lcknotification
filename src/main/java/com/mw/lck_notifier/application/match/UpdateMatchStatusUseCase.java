package com.mw.lck_notifier.application.match;

import com.mw.lck_notifier.domain.match.Match;
import com.mw.lck_notifier.domain.match.MatchRepository;
import com.mw.lck_notifier.domain.match.MatchStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMatchStatusUseCase {

    private final MatchRepository matchRepository;

    public UpdateMatchStatusUseCase(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional
    public void update(Long matchId, MatchStatus status) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found: " + matchId));

        match.changeStatus(status);

        matchRepository.save(match);
    }
}
