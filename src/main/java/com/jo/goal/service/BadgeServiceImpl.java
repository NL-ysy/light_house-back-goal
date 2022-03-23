package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.Goal;
import com.jo.goal.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final GoalService goalService;

    @Transactional
    @Override
    public Badge addBadge(Badge badge, Long goalId) {
        log.info("add badge!");

        Goal goal = goalService.getGoalById(goalId).get(); // id에 해당하는 목표

        if(goal != null && goal.getState() == 1) { // 목표가 존재하고 해당 목표가 완료 상태일 때
            int endPoint = badge.endDayPoint(goal);
            int completePoint = badge.completePoint(goal);
            badge.setBadgePoint(endPoint + completePoint);
            return badgeRepository.save(badge);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Badge editBadge(Badge badge) {
        log.info("edit badge {}!", badgeRepository.findById(badge.getId()).get());
        return badgeRepository.save(badge);
    }

    @Transactional
    @Override
    public List<Badge> getAllBadge() {
        log.info("get All badge!");
        return badgeRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Badge> getBadgeById(Long id) {
        log.info("get badge by badge id {}!", id);
        return Optional.ofNullable(badgeRepository.findById(id).get());
    }

    @Transactional
    @Override
    public void delBadge(Long id) {
        log.info("del badge by id {}!", id);
        badgeRepository.deleteById(id);
    }
}
