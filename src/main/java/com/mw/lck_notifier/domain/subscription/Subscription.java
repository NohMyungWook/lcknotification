package com.mw.lck_notifier.domain.subscription;

import com.mw.lck_notifier.domain.device.Device;
import com.mw.lck_notifier.domain.team.Team;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@Table(
        name = "subscription",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_subscription_device_team",
                        columnNames = {"device_id", "team_id"}
                )
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    protected Subscription() {
        // JPA only
    }

    private Subscription(Device device, Team team) {
        this.device = device;
        this.team = team;
        this.enabled = true;
    }

    /**
     * 최초 구독 생성
     */
    public static Subscription subscribe(Device device, Team team) {
        return new Subscription(device, team);
    }

    /**
     * 알림 비활성화
     */
    public void disable() {
        this.enabled = false;
    }

    /**
     * 알림 재활성화
     */
    public void enable() {
        this.enabled = true;
    }
}
