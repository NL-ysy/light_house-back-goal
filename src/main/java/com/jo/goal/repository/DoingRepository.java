package com.jo.goal.repository;

import com.jo.goal.model.Doing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoingRepository extends JpaRepository<Doing, Long> {
}
