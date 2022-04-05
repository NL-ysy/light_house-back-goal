package com.jo.goal.repository;

import com.jo.goal.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findAllByType(String type);
}
