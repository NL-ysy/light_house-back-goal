package com.jo.goal.repository;

import com.jo.goal.model.BadgeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeListRepository extends JpaRepository<BadgeList, Long> {
    List<BadgeList> findByUserIdOrderByIdDesc(Long userId);
    BadgeList findByBadgeIdAndUserId(Long badgeId, Long userId);
    List<BadgeList> findAllByTypeAndUserId(String type, Long userId);
}
