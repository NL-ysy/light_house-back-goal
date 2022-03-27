package com.jo.goal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String badgeName;
    private String badgeDesc;
    private String badgeImg;
    private int badgePoint = 0;


    @Builder
    public Badge(String badgeName, String badgeDesc, String badgeImg, int badgePoint) {
        this.badgeName = badgeName;
        this.badgeDesc = badgeDesc;
        this.badgeImg = badgeImg;
        this.badgePoint = badgePoint;
    }

    // 총 진행 기간에 따른 포인트 부여
    public int endDayPoint(Goal goal) {
        int point = 0;
        if(goal.getTotalCount() < 30) {
            point = 0;
        } else if(goal.getTotalCount() < 60) {
            point = 50;
        } else if(goal.getTotalCount() < 90) {
            point = 100;
        } else if(goal.getTotalCount() <= 180) {
            point = 300;
        }
        return point;
    }

}
