package com.jo.goal.service;

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
            Goal editedGoal = new Goal();
            editedGoal = Goal.builder()
                    .id(goal.getId())
                    .goalTitle(goal.getGoalTitle())
                    .goalDesc(goal.getGoalDesc())
                    .startDay(goal.getStartDay())
                    .endDay(goal.getEndDay())
                    .weekCount(goal.getWeekCount())
                    .count(goal.getCount())
                    .totalCount(goal.getTotalCount())
                    .doing(goal.getDoing())
                    .state(goal.getState())
                    .build();
            goalRepository.save(goal);
            return editedGoal;
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
}
