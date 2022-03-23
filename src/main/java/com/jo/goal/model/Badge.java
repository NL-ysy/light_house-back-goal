package com.jo.goal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;

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
        if(goal.getTotalCount() < 30) {
            badgePoint = 0;
        } else if(goal.getTotalCount() < 60) {
            badgePoint = 50;
        } else if(goal.getTotalCount() < 90) {
            badgePoint = 100;
        } else if(goal.getTotalCount() < 180) {
            badgePoint = 300;
        } else if(goal.getTotalCount() < 365) {
            badgePoint = 1000;
        } else if(goal.getTotalCount() == 365) {
            badgePoint = 2000;
        }
        return badgePoint;
    }

    // 실행율에 따른 포인트 부여
    public int completePoint(Goal goal) {
//        DayOfWeek startDayOfWeek = goal.getStartDay().getDayOfWeek(); // 시작일의 요일
//        int startDayNum = startDayOfWeek.getValue(); // 월요일 1 ~ 일요일 7
//        int exceptFirstWeekCount = goal.getTotalCount() - (7 - startDayNum); // 첫주 제외 실행일 수
        int totalWeek = (int)Math.ceil(goal.getTotalCount() / 7); // 목표 기간이 몇주인지
        int remainderDay = goal.getTotalCount() % 7; // 몇주인지 계산하고 남는 일자
        int totalDate = totalWeek * goal.getWeekCount() + remainderDay; // 한 주에 실행할 획수 * week + 남은 일수

        if(totalDate / goal.getCount() == 1) {
            badgePoint = 15;
        } else if(totalDate / goal.getCount() >= 0.9) {
            badgePoint = 10;
        } else if(totalDate / goal.getCount() >= 0.8) {
            badgePoint = 5;
        } else {
            badgePoint = 0;
        }
        return badgePoint;
    }
}
