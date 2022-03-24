package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.Goal;
import com.jo.goal.model.GoalDto;
import com.jo.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public Goal editGoal(GoalDto goalDto) {
//        log.info("edit goal. {}", goalRepository.findById(goal.getId()).get());

        Goal goal = goalRepository.findById(goalDto.getId()).get();

        if(goal != null && goal.getState() == 0) { // 목표 진행 중일 때
            int count = goalDto.getDoing(); // 일일 목표 실행하면 1
            int prevCount = goal.getCount();
            goal.setCount(prevCount + count);
        } else {
            log.error("complete goal : {}", goal.getId());
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

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void scheduler() {
        List<Goal> list = goalRepository.findAll();
        LocalDate today = LocalDate.now();
        list.forEach(goal -> {
            goal.setDoing(0); // goal의 doing 상태를 0으로 전환
            if(goal.getEndDay().isBefore(today)) {
                goal.setState(1); //endDay 확인하고 state 변경

                // 목표 달성 여부 파악
                // 목표 달성을 성공했을 때 배지 생성
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
        });
    }

    @Scheduled(cron = "30 * * * * *")
    public void testScheduler() {
        System.out.println("test");
    }

}
