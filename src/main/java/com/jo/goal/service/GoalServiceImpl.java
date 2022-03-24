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

        if(goal.isEnd(goalDto.getEndDay()) == 0) { // 목표 진행 중일 때(종료일 당일까지)
            int count = goalDto.getDoing(); // 일일 목표 실행하면 1
            int prevCount = goal.getCount();
            goal.setCount(prevCount + count);
//            goal.setDoing(0); // 오늘이 지났을 때 setDoing을 0으로 변경

        } else if(goal.isEnd(goalDto.getEndDay()) == 1) { // 목표 종료일이 지났을 때

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

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void scheduler(GoalDto goalDto) {
        Goal goal = goalRepository.findById(goalDto.getId()).get();
        goal.setDoing(0); // Doing이 1일 경우 0으로 변경되어 다음 날 다시 count 가능

        LocalDate today = LocalDate.now();
        if(goal.getEndDay().isBefore(today)) { // 목표 종료일이 지났을 때
            goal.setState(1); // 목표 종료 상태로 변경
            editGoal(goalDto);
        }
    }

}
