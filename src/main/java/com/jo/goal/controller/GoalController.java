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
    public String add(@RequestBody GoalDto goal) {
        if(goalService.addGoal(goal)) {
            return null;
        } else {
            return "목표는 최대 3개까지 설정 가능합니다.";
        }
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

    @GetMapping("/goal/result/{state}/{result}/{userId}")
    public Long countByStateAndResultAndUserId(@PathVariable int state, @PathVariable boolean result, @PathVariable Long userId) {
        return goalService.countByStateAndResultAndUserId(state, result, userId);
    }

    @GetMapping("/goal/{state}/{userId}/search")
    public List<Goal> searchGoal(@PathVariable int state, @PathVariable Long userId, @RequestParam(value = "keyword") String keyword) {
        return goalService.findByStateAndUserIdAndGoalTitleContaining(state, userId, keyword);
    }
}