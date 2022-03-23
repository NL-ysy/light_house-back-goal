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
}
