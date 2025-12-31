-- =========================================================
-- LCK Notifier MVP Schema
-- Version: V1
-- Description: Initial schema for LCK match notification service
-- =========================================================

-- =========================================================
-- Device: 알림을 받을 주체 (로그인 없음, 디바이스 기준)
-- =========================================================
CREATE TABLE device (
    id           BIGSERIAL PRIMARY KEY,
    push_token   VARCHAR(255) NOT NULL,
    platform     VARCHAR(20)  NOT NULL, -- IOS / ANDROID
    created_at   TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT uk_device_push_token UNIQUE (push_token)
);

-- =========================================================
-- Team: LCK 팀 (고정 도메인, 시드 데이터)
-- =========================================================
CREATE TABLE team (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(50)  NOT NULL,   -- T1, GEN, DK, etc...
    display_name  VARCHAR(100) NOT NULL,   -- T1, Gen.G, Dplus Kia
    is_active     BOOLEAN      NOT NULL DEFAULT true
);

-- =========================================================
-- Subscription: 디바이스가 구독한 팀
-- =========================================================
CREATE TABLE subscription (
    id         BIGSERIAL PRIMARY KEY,
    device_id  BIGINT NOT NULL,
    team_id    BIGINT NOT NULL,
    enabled    BOOLEAN NOT NULL DEFAULT true,

    CONSTRAINT fk_subscription_device
      FOREIGN KEY (device_id) REFERENCES device(id),

    CONSTRAINT fk_subscription_team
      FOREIGN KEY (team_id) REFERENCES team(id),

    CONSTRAINT uk_subscription_device_team
      UNIQUE (device_id, team_id)
);

-- =========================================================
-- MatchDay: 하루 단위 경기일
-- =========================================================
CREATE TABLE match_day (
    id          BIGSERIAL PRIMARY KEY,
    match_date  DATE NOT NULL,

    CONSTRAINT uk_match_day_date UNIQUE (match_date)
);

-- =========================================================
-- Match: 팀 vs 팀 경기 (BO3 / BO5)
-- =========================================================
CREATE TABLE match (
    id            BIGSERIAL PRIMARY KEY,
    match_day_id  BIGINT NOT NULL,
    team_a_id     BIGINT NOT NULL,
    team_b_id     BIGINT NOT NULL,
    best_of       INT NOT NULL,            -- 3 or 5
    match_order   INT NOT NULL,            -- 하루 중 1, 2
    status        VARCHAR(20) NOT NULL,    -- SCHEDULED / IN_PROGRESS / FINISHED

    CONSTRAINT fk_match_match_day
       FOREIGN KEY (match_day_id) REFERENCES match_day(id),

    CONSTRAINT fk_match_team_a
       FOREIGN KEY (team_a_id) REFERENCES team(id),

    CONSTRAINT fk_match_team_b
       FOREIGN KEY (team_b_id) REFERENCES team(id)
);

-- =========================================================
-- MatchSet: 실제 게임 단위 (알림 트리거 기준)
-- =========================================================
CREATE TABLE match_set (
    id           BIGSERIAL PRIMARY KEY,
    match_id     BIGINT NOT NULL,
    set_number   INT NOT NULL,             -- 1 ~ 5
    state        VARCHAR(20) NOT NULL,     -- WAITING / PICK_BAN / IN_GAME / FINISHED
    detected_at  TIMESTAMP,

    CONSTRAINT fk_match_set_match
       FOREIGN KEY (match_id) REFERENCES match(id),

    CONSTRAINT uk_match_set_unique
       UNIQUE (match_id, set_number)
);

-- =========================================================
-- NotificationLog: 알림 중복 방지 (신뢰도의 핵심)
-- =========================================================
CREATE TABLE notification_log (
    id         BIGSERIAL PRIMARY KEY,
    device_id  BIGINT NOT NULL,
    match_id   BIGINT NOT NULL,
    type       VARCHAR(50) NOT NULL,       -- MATCH_START
    sent_at    TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT fk_notification_device
      FOREIGN KEY (device_id) REFERENCES device(id),

    CONSTRAINT fk_notification_match
      FOREIGN KEY (match_id) REFERENCES match(id),

    CONSTRAINT uk_notification_once
      UNIQUE (device_id, match_id, type)
);
