package com.jo.goal.service;

import com.jo.goal.model.BadgeList;
import com.jo.goal.model.Goal;

import java.util.List;
import java.util.Optional;

public interface BadgeListService {
    void save(BadgeList badgeList);
    List<BadgeList> findAll();
    Optional<BadgeList> findById(Long id);
    void delBadgeList(Long id);
    List<BadgeList> findAllByUserId(Long userId);
    BadgeList addBadgeList(Goal goal);
}
