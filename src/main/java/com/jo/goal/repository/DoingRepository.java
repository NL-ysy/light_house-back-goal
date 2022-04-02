package com.jo.goal.repository;

import com.jo.goal.model.Doing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DoingRepository extends JpaRepository<Doing, Long> {
    List<Doing> findAllByGoalId(Long goalId);
    List<Doing> findAllByWeek(int week);
}
