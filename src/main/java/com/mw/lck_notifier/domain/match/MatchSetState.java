package com.mw.lck_notifier.domain.match;

public enum MatchSetState {
    WAITING,      // 아직 시작 전
    PICK_BAN,     // 밴픽 감지됨
    IN_GAME,      // 진행 중
    FINISHED      // 종료됨
}
