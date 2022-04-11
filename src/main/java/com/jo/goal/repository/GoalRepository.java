package com.jo.goal.repository;

import com.jo.goal.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findTop3ByStateOrderByIdDesc(int state); // state 값에 따라 내림차순으로 3개만 조회하는 쿼리
    List<Goal> findAllByUserId(Long userId);
}