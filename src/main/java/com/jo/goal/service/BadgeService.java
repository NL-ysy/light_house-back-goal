package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.Goal;

import java.util.List;
import java.util.Optional;

public interface BadgeService {

    Badge addBadge(Badge badge);
    Badge editBadge(Badge badge);
    List<Badge> getAllBadge();
    Optional<Badge> getBadgeById(Long id);
    void delBadge(Long id);
    List<Badge> findAllByType(String type);
    Optional<Badge> findByBadgeName(String badgeName);
    Badge isComplete(Goal goal);
}
