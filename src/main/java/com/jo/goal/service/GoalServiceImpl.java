package com.jo.goal.service;

import com.jo.goal.controller.GoalDto;
import com.jo.goal.model.*;
import com.jo.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final BadgeService badgeService;
    private final DoingService doingService;
    private final BadgeListService badgeListService;

    @Transactional
    @Override
    public boolean addGoal(GoalDto goalDto) {
        log.info("add goal");
        List<BadgeList> badgeLists = badgeListService.findAllByUserId(goalDto.getUserId());
        List<Goal> goalList = goalRepository.findAllByUserId(1L);

        if(badgeLists.size() < 1 && goalList.size() < 1) { // 사용자가 처음 목표를 생성했을 때 기념 배지 증정
            BadgeList badgeList = new BadgeList(badgeService.createFirstGoal(), 0, 1, LocalDate.now(), "Special", goalDto.getUserId());
            if(!badgeLists.contains("Set First Goal")) {
                badgeListService.save(badgeList);
            }
        }

        log.info("user id : {}", goalDto.getUserId());
        log.info("goal list size : {}", goalList.size());
        if(goalList.size() < 3) { // 목표 최대 3개까지 설정
            goalRepository.save(
                    new Goal(
                            goalDto.getGoalTitle(),
                            goalDto.getGoalDesc(),
                            goalDto.getStartDay(),
                            goalDto.getEndDay(),
                            goalDto.getPeriod(),
                            goalDto.getWeekCount(),
                            goalDto.getTotalCount(),
                            goalDto.getCount(), // test용 count 갯수 조절 가능
                            goalDto.getUserId()
                    )
            );
            return true;
        } else {
            return false;
        }
    }

    public int checkWeek(Goal goal) { // 총 실행 기간 중 현재 몇 주차인지 확인
        int now = LocalDate.now().getDayOfYear();
        int start = goal.getStartDay().getDayOfYear();
        int week = 1;

        for(int i = 1; i <= now - start; i++) {
            if(i % 7 == 0) {
                week++;
            }
        }

        log.info("week : {}", week);
        return week;
    }


    public Goal checkDoing(GoalDto goalDto) { // goal Count and add Doing
        log.info("checkDoing by goalId : {}", goalDto.getId());
        Goal goal = goalRepository.findById(goalDto.getId()).get();

        int thisWeek = checkWeek(goal);

        if(goal.getState() == 0 && goal.getCount() < goal.getTotalCount()) {
            if((doingService.findAllByWeekAndGoalId(thisWeek, goal.getId()).size() < goal.getWeekCount())) { // 일주일 동안 실천하기로 한 횟수만큼 count
                if(doingService.findByGoalIdAndCheckDate(goal.getId(), LocalDate.now()) == null) { // 하루에 1번만 목표 실천 인증 가능
                    log.info("checkDoing");
//                    goal.setCount(goalDto.getCount()); // test용
                    goal.setCount(goal.getCount() + 1);
                    doingService.addDoing(Doing.builder()
                            .goal(goal)
                            .checkDate(LocalDate.now())
                            .week(thisWeek)
                            .postId(goalDto.getPostId())
                            .build());
                }
            }
        } else {
            log.error("check doing error");
        }

        return goalRepository.save(goal);
    }

    @Transactional
    @Override
    public Goal editGoal(GoalDto goalDto) {
        log.info("edit goal. {}", goalRepository.findById(goalDto.getId()).get());
        Goal goal = null;
        try{
            goal = checkDoing(goalDto);
        } catch (Exception e) {
            log.error("edit goal error : {}", e.getMessage());
        }

//        Goal goal = checkDoing(goalDto);
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
    public void delGoal(Long id, Long userId) {
        log.info("delete goal by id {}.", id);
        Optional<Goal> goal = goalRepository.findByIdAndUserId(id, userId);
        if(goal.isPresent()) {
            goalRepository.deleteById(id);
        }
    }

    @Override
    public List<Goal> get3DoingGoal(int state, Long userId) {
        log.info("대시보드에 보여줄 최근 진행중인 목표 3개");
        return goalRepository.findTop3ByStateAndUserIdOrderByIdDesc(state, userId);
    }

    public List<Goal> sortOrderByDesc() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return goalRepository.findAll(sort);
    }

    @Override
    public Long countByStateAndResultAndUserId(int state, boolean result, Long userId) {
        return goalRepository.countByStateAndResultAndUserId(state, result, userId);
    }

    @Override
    public List<Goal> findAllByUserId(Long goalId) {
        return goalRepository.findAllByUserId(goalId);
    }

//    @Scheduled(fixedDelay = 1000 * 30) // 30초에 한 번씩 실행
    @Scheduled(cron = "30 * * * * *") // 매분 30초마다 실행
//    @Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void scheduler() { // 목표 종료일에 state 변경
        List<Goal> list = goalRepository.findAll();
        LocalDate today = LocalDate.now();

        list.forEach(goal -> {
            if(goal.getEndDay().isBefore(today) && goal.getState() == 0) {
                goal.setState(1); //endDay 확인하고 state 변경(종료되면 1)

                Badge badge = badgeService.isComplete(goal); // 목표를 80% 이상 달성한 경우 사용자의 배지리스트에 배지 추가

                if(badge != null) {
                    goal.setResult(true); // 성공하면 result true로 변경
                    BadgeList badgeList = badgeListService.findByBadgeIdAndUserId(badge.getId(), goal.getUserId());
                    if(badgeList == null) { // 배지리스트에 같은 배지가 존재하지 않는 경우
                        badgeList = new BadgeList(badge, badge.getPoint(), 1, LocalDate.now(), badge.getType(), goal.getUserId());
                        badgeListService.save(badgeList);
                    } else { // 배지리스트에 동일한 배지가 존재하는 경우 - 포인트, 갯수 증가, 날짜 업데이트
                        badgeList.setPoint(badgeList.getPoint() + badgeList.getPoint());
                        badgeList.setCount(badgeList.getCount() + 1);
                        badgeList.setDate(LocalDate.now());
                        badgeListService.save(badgeList);
                    }

//                    List<Goal> goalList = goalRepository.findAllByUserId(goal.getUserId());
//                    List<BadgeList> badgeLists = badgeListService.findAllByUserId(goal.getUserId());
//                    if(goal.getId() == goalList.get(0).getId() && !badgeLists.contains("Achieve a First Goal")) { // 사용자가 목표를 처음 달성한 경우 기념 배지 증정
//                        badgeListService.save(
//                                new BadgeList(badgeService.achieveFirstGoal(), 0, 1, LocalDate.now(), "Special", goal.getUserId())
//                        );
//                    }
                } else {
                    log.info("goal fail");
                }

                goalRepository.save(goal);
            }
        });
    }

}
