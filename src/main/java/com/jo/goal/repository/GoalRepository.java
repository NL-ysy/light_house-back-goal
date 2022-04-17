package com.jo.goal.repository;

import com.jo.goal.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findTop3ByStateAndUserIdOrderByIdDesc(int state, Long userId); // state 값에 따라 내림차순으로 3개만 조회하는 쿼리
    List<Goal> findAllByStateAndUserId(int state, Long userId);
    List<Goal> findAllByUserId(Long userId);
    Optional<Goal> findByIdAndUserId(Long id, Long userId);
    Long countByStateAndResultAndUserId(int state, boolean result, Long userId);
}