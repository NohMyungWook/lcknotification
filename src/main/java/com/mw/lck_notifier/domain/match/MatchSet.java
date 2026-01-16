package com.mw.lck_notifier.domain.match;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "match_set")
@Getter
public class MatchSet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private Match match;
    private int setNumber;

    @Enumerated(EnumType.STRING)
    private MatchSetState state;

    protected MatchSet() {}

    public static MatchSet waiting(Match match, int number) {
        MatchSet set = new MatchSet();
        set.match = match;
        set.setNumber = number;
        set.state = MatchSetState.WAITING;
        return set;
    }
}
