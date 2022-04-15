package com.jo.goal.controller;

import com.jo.goal.model.Goal;
import com.jo.goal.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/goal")
    public void add(@RequestBody GoalDto goal) {
        goalService.addGoal(goal);
    }

    @PutMapping("/goal/{id}")
    public void edit(@RequestBody GoalDto goal, @PathVariable Long id) {goalService.editGoal(goal);}

    @GetMapping("/goal")
    public List<Goal> getAll(){return goalService.getAllGoal();}

    @GetMapping("/goal/{id}")
    public Optional<Goal> getGoalById(@PathVariable Long id) {return goalService.getGoalById(id);}

    @DeleteMapping("/goal/{id}/{userId}")
    public void delete(@PathVariable("id") Long id, @PathVariable Long userId) {goalService.delGoal(id, userId);}

    @GetMapping("/dGoal/{state}/{userId}")
    public List<Goal> get3dGoals(@PathVariable int state, @PathVariable Long userId) {
        return goalService.get3DoingGoal(state, userId);
    }
}