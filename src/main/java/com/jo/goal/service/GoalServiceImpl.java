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
        List<Goal> list = goalRepository.findAll();
        if(list.size() == 0) { // 첫 목표 생성 기념 배지
            badgeService.addBadge(Badge.builder()
                        .badgeName("First Badge")
                        .badgeDesc("Set Goal")
                        .build());
        }
        
        return goalRepository.save(goal);
    }

    @Transactional
    @Override
    public int editGoal(GoalDto goalDto) {
//        log.info("edit goal. {}", goalRepository.findById(goal.getId()).get());

        Goal goal = goalRepository.findById(goalDto.getId()).get();
        int todayCount = goal.getCount();

        if(goal != null && goal.getState() == 0 && goal.getDoing() == 0) { // 목표 진행 중일 때
            int count = goalDto.getDoing(); // 일일 목표 실행하면 1
            int prevCount = goal.getCount();
            goal.setCount(prevCount + count);
            goal.setDoing(1);
        } else {
            log.error("complete goal : {}", goal.getId());
        }

        return todayCount;
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

    public Badge isComplete(Goal goal) { // 목표 달성 여부 파악하고 실행율에 따른 배지 및 포인트 생성
        int totalWeek = (int)Math.ceil(goal.getTotalCount() / 7); // 목표 기간이 몇주인지
        int remainderDay = goal.getTotalCount() % 7; // 몇주인지 계산하고 남는 일자
        int totalDate = totalWeek * goal.getWeekCount() + remainderDay; // 한 주에 실행할 횟수 * week + 남은 일수

        Badge badge = null;

        if(totalDate / goal.getCount() == 1) {
            log.info("100");
            badge = new Badge();
            badge.setBadgePoint(15);
        } else if(totalDate / goal.getCount() >= 0.9) {
            log.info("90");
            badge = new Badge();
            badge.setBadgePoint(10);
        } else if(totalDate / goal.getCount() >= 0.8) {
            log.info("80");
            badge = new Badge();
            badge.setBadgePoint(5);
        } else {
            log.error("fail");
            return null;
        }

        return badge;
    }

    int weekCheck= 0; // 일주일 기간 check
    int weekCheckCount = 0; // 한 주에 실천한 count check
//    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    @Scheduled(fixedDelay = 1000 * 30) // 30초에 한 번씩 실행
    public void scheduler() { // 목표 doing 초기화, 목표 종료일에 state 변경
        List<Goal> list = goalRepository.findAll();
        LocalDate today = LocalDate.now();

        list.forEach(goal -> {
//            goal.setDoing(0); // goal의 doing 상태를 0으로 전환

            // 일주일에 실행한 목표 실천 횟수 판별
            weekCheck++; // 매일 count
            log.info("weekCheck : {}", weekCheck);
            if(goal.getDoing() == 1) weekCheckCount++; // 해당일에 목표를 실천했을 때 count
            if(weekCheckCount < goal.getWeekCount()) {
                log.info("weekCheckCount : {}", weekCheckCount);
                goal.setDoing(0);
                goalRepository.save(goal);
            } else {
                goal.setDoing(1);
            }
            if(weekCheck == 7) {
                log.info("7");
                weekCheck = 0;
                weekCheckCount = 0;
                goal.setDoing(0);
                goalRepository.save(goal);
            }

            // 목표 종료일 판별
            if(goal.getEndDay().isBefore(today)) {
                if(goal.getState() == 1) {
                    return;
                } else {
                    goal.setState(1); //endDay 확인하고 state 변경

                    Badge badge = isComplete(goal); // 목표를 달성했을 때 배지 생성
                    if(badge != null) {
                        log.info("get badge");
                        int point = badge.endDayPoint(goal);
                        badge.setBadgePoint(badge.getBadgePoint() + point);
                        if(goal.getId() == 1L) { // 처음 생성한 목표를 성공했을 때 주는 기념 배지
                            List<Badge> badgeList = badgeService.getAllBadge();
                            if(!badgeList.contains("Second Badge")) { // 기념 배지가 없으면 배지 증정
                                badgeService.addBadge(Badge.builder()
                                        .badgeName("Second Badge")
                                        .badgeDesc("Complete First Goal")
                                        .build());
                            }
                        }
                        badgeService.addBadge(badge);
                    } else {
                        log.error("fail : {}", goal.getId());
                    }
                }

//                goalRepository.save(goal);
            }
        });
    }

//    @Scheduled(cron = "30 * * * * *") // 매분 30초마다 실행
//    public void testScheduler() {
//        System.out.println("test");
//    }

}
