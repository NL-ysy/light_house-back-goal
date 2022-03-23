package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.Goal;
import com.jo.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoalServiceImpl implements GoalService{

    private final GoalRepository goalRepository;
    private final BadgeService badgeService;

    @Transactional
    @Override
    public Goal addGoal(Goal goal) {
        log.info("add goal");

        return goalRepository.save(goal);
    }

    @Transactional
    @Override
    public Goal editGoal(Goal goal) {
        log.info("edit goal. {}", goalRepository.findById(goal.getId()).get());

        if(goal.isEnd(goal.getEndDay()) == 0) { // 목표 진행 중일 때(종료일 당일까지)
//            int count = goal.getDoing(); // 일일 목표 실행하면 1
//            int prevCount = goal.getCount();
//            goal.setCount(prevCount + count);
//            goal.setDoing(0);

        } else if(goal.isEnd(goal.getEndDay()) == 1) { // 목표 종료일이 지났을 때

            Badge badge = new Badge();
            int endPoint = badge.endDayPoint(goal); // 목표 기간에 따른 포인트
            int completePoint = badge.completePoint(goal); // 목표 성공율에 따른 포인트

            if(goal.getId() == 1L && completePoint > 0) { // 처음 생성한 목표를 성공했을 때 부여되는 기념 배지
                badgeService.addBadge(Badge.builder()
                        .badgeName("Second Badge")
                        .badgeDesc("Complete First Goal")
                        .build());
            }

            badge.setBadgePoint(endPoint + completePoint);
            badgeService.addBadge(badge);

        }

        return goalRepository.save(goal);

    }

    @Transactional
    @Override
    public List<Goal> getAllGoal() {
        log.info("get all goal");
        return goalRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Goal> getGoalById(Long id) {
        log.info("get goal by goal id {}.", id);
        return Optional.ofNullable(goalRepository.findById(id).get());
    }

    @Transactional
    @Override
    public void delGoal(Long id) {
        log.info("del goal by id {}.", id);
        goalRepository.deleteById(id);
    }
}
