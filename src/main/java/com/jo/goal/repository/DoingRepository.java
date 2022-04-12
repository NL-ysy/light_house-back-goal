package com.jo.goal.repository;

import com.jo.goal.model.Doing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface DoingRepository extends JpaRepository<Doing, Long> {
    List<Doing> findAllByGoalId(Long goalId);
    List<Doing> findAllByWeek(int week);
    List<Doing> findAllByWeekAndGoalId(int week, Long goalId);
    Doing findByGoalIdAndCheckDate(Long goalId, LocalDate localDate);
}
