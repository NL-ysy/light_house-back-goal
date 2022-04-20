package com.jo.goal.service;

import com.jo.goal.model.Goal;
import com.jo.goal.controller.GoalDto;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    boolean addGoal(GoalDto goalDto);
    Goal editGoal(GoalDto goalDto);
    List<Goal> getAllGoal();
    Optional<Goal> getGoalById(Long id);
    void delGoal(Long id, Long userId);
    List<Goal> get3DoingGoal(int state, Long userId);
    List<Goal> findAllByUserId(Long goalId);
    Long countByStateAndResultAndUserId(int state, boolean result, Long userId);
    List<Goal> findByStateAndUserIdAndGoalTitleContaining(int state, Long userId, String keyword);
}