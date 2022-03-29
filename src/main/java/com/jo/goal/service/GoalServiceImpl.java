package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.Doing;
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
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final BadgeService badgeService;
    private final DoingService doingService;

    @Transactional
    @Override
    public Goal addGoal(Goal goal) {
        log.info("add goal");

        List<Goal> list = goalRepository.findAll();
        if(list.size() < 1) { // 첫 목표 생성 기념 배지
            badgeService.addBadge(Badge.builder()
                    .badgeName("First Badge")
                    .badgeDesc("Set Goal")
                    .build());
        }

        return goalRepository.save(goal);
    }

    int week = 1; // 목표 시작 주 (1주차 일 때부터 시작)
    public void checkDoing(Goal goal) { // 일주일 동안의 목표 체크
        log.info("checkDoing by goalId : {}", goal.getId());

        List<Doing> list = doingService.getAllDoing();

        if(goal.getState() == 0 && list.size() < goal.getWeekCount() * week) {
            log.info("checkDoing");
            LocalDate now = LocalDate.now(); // 현재
            LocalDate endWeek = goal.getStartDay().plusWeeks(week); // 목표 시작일부터 week의 주 만큼 지난 날 (1주차 일 때 -> 8일 째 되는 날)

            if(now.isBefore(endWeek)) { // 이번 주 목표 실천 counting
                log.info("this week : {}", week);
                goal.setCount(goal.getCount() + 1);

                doingService.addDoing(Doing.builder()
                        .goal(goal)
                        .checkDate(LocalDate.now())
                        .build());
            } else if(now.isEqual(endWeek) || now.isAfter(endWeek)) { // 다음 주로 넘어갈 때 (1주차 일 때 -> 8일 이상인 날)
                week++; // week = 2
                log.info("next week : {}", week);
                checkDoing(goal); // 다음 일주일 동안의 목표 체크
            }
        } else {
            log.error("checkDoing error");
        }

        goalRepository.save(goal);
    }

    @Transactional
    @Override
    public Goal editGoal(GoalDto goalDto) {
//        log.info("edit goal. {}", goalRepository.findById(goal.getId()).get());
//        Goal editedGoal = new Goal();
//        editedGoal = Goal.builder()
//                .id(goal.getId())
//                .goalTitle(goal.getGoalTitle())
//                .goalDesc(goal.getGoalDesc())
//                .startDay(goal.getStartDay())
//                .endDay(goal.getEndDay())
//                .weekCount(goal.getWeekCount())
//                .count(goal.getCount())
//                .totalCount(goal.getTotalCount())
//                .state(goal.getState())
//                .build();
//        goalRepository.save(goal);
//        return editedGoal;

        Goal goal = goalRepository.findById(goalDto.getId()).get();
        checkDoing(goal);

        return goalRepository.save(goal);
    }

    @Transactional
    @Override
    public List<Goal> getAllGoal() {
        log.info("get all goal!!!!!!!!!!!!!");
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

    @Override
    public List<Goal> get3DoingGoal(int state) {
        log.info("대시보드에 보여줄 최근 진행중인 목표 3개");
        return goalRepository.findTop3ByStateOrderByIdDesc(state);
    }

    public Badge isComplete(Goal goal) { // 목표 달성 여부 파악하고 실행율에 따른 배지 및 포인트 생성
        Badge badge = null;

        if(goal.getTotalCount() / goal.getCount() == 1) { // 100% 달성
            log.info("100");
            badge = new Badge();
            badge.setBadgePoint(15);
        } else if(goal.getTotalCount() / goal.getCount() >= 0.9) { // 90% 달성
            log.info("90");
            badge = new Badge();
            badge.setBadgePoint(10);
        } else if(goal.getTotalCount() / goal.getCount() >= 0.8) { // 80% 달성
            log.info("80");
            badge = new Badge();
            badge.setBadgePoint(5);
        } else {
            log.error("fail");
            return null;
        }

        return badge;
    }


//    @Scheduled(fixedDelay = 1000 * 30) // 30초에 한 번씩 실행
//    @Scheduled(cron = "30 * * * * *") // 매분 30초마다 실행
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void scheduler() { // 목표 종료일에 state 변경
        List<Goal> list = goalRepository.findAll();
        LocalDate today = LocalDate.now();

        list.forEach(goal -> {
            if(goal.getEndDay().isBefore(today)) {
                goal.setState(1); //endDay 확인하고 state 변경(종료되면 1)

                Badge badge = isComplete(goal); // 목표를 달성했을 때 배지 생성
                if(badge != null) {
                    log.info("set result and get badge");
                    goal.setResult(true); // 목표 달성에 성공한 경우 true
                    int point = badge.endDayPoint(goal); // 설정한 목표 기간에 따른 배지 증정
                    badge.setBadgePoint(badge.getBadgePoint() + point);
                    if (goal.getId() == 1L) { // 처음 생성한 목표를 성공했을 때 주는 기념 배지
                        List<Badge> badgeList = badgeService.getAllBadge();
                        if (!badgeList.contains("Second Badge")) { // 첫 목표 생성 기념 배지가 없을 때만 배지 증정
                            badgeService.addBadge(Badge.builder()
                                    .badgeName("Second Badge")
                                    .badgeDesc("Complete First Goal")
                                    .build());
                        }
                    }
                    badgeService.addBadge(badge);
                }
                goalRepository.save(goal);
            }
        });
    }

}
