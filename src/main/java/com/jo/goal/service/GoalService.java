package com.jo.goal.service;

import com.jo.goal.model.Goal;
import com.jo.goal.model.GoalDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    Goal addGoal(Goal goal);
    Goal editGoal(GoalDto goalDto);
    List<Goal> getAllGoal();
    Optional<Goal> getGoalById(Long id);
    void delGoal(Long id);
    List<Goal> get3DoingGoal(int state);
}