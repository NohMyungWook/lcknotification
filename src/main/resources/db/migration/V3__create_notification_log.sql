-- =========================================================
-- NotificationLog: 알림 중복 방지 + 이력 저장
-- =========================================================

CREATE TABLE notification_log (
    id         BIGSERIAL PRIMARY KEY,
    device_id  BIGINT NOT NULL,
    match_id   BIGINT NOT NULL,
    type       VARCHAR(50) NOT NULL,       -- MATCH_START, MATCH_SET_START 등
    sent_at    TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT fk_notification_log_device
      FOREIGN KEY (device_id) REFERENCES device(id),

    CONSTRAINT fk_notification_log_match
      FOREIGN KEY (match_id) REFERENCES matches(id),

    CONSTRAINT uk_notification_once
      UNIQUE (device_id, match_id, type)
);
