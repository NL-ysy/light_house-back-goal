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
    public void add(@RequestBody Goal goal) {goalService.addGoal(goal);}

    @PutMapping("/goal")
    public void edit(@RequestBody Goal goal) {goalService.editGoal(goal);}

    @GetMapping("/goal")
    public List<Goal> getAll(){return goalService.getAllGoal();}

    @GetMapping("/goal/{id}")
    public Optional<Goal> getGoalById(@PathVariable Long id) {return goalService.getGoalById(id);}

    @DeleteMapping("/goal/{id}")
    public void del(@PathVariable("id") Long id) {goalService.delGoal(id);}

    @GetMapping("/dGoal/{state}")
    public List<Goal> get3dGoals(@PathVariable int state) {
        return goalService.get3DoingGoal(state);
    }
}