package com.jo.goal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class BadgeList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "badge_id")
    @ManyToOne
    private Badge badge;
    private int point = 0;
    private int count;
    private LocalDate date;
    private String type;
    private Long userId;

    public BadgeList(Badge badge, int point, int count, LocalDate date, String type, Long userId) {
        this.badge = badge;
        this.point = point;
        this.count = count;
        this.date = date;
        this.type = type;
        this.userId = userId;
    }

    // 총 진행 기간에 따른 포인트 부여
    public int endDayPoint(Goal goal) {
        int point = 0;
        if(goal.getPeriod() < 30) {
            point = 0;
        } else if(goal.getPeriod() < 60) {
            point = 50;
        } else if(goal.getPeriod() < 90) {
            point = 100;
        } else if(goal.getPeriod() <= 180) {
            point = 300;
        }
        return point;
    }
}
