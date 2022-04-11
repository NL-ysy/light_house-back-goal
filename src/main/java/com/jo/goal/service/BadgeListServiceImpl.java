package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.BadgeList;
import com.jo.goal.model.Goal;
import com.jo.goal.repository.BadgeListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BadgeListServiceImpl implements BadgeListService{

    private final BadgeListRepository badgeListRepository;
    private final BadgeService badgeService;

    @Override
    public void save(BadgeList badgeList) {
        badgeListRepository.save(badgeList);
    }

    @Override
    public List<BadgeList> findAll() {
        return badgeListRepository.findAll();
    }

    @Override
    public Optional<BadgeList> findById(Long id) {
        return Optional.ofNullable(badgeListRepository.findById(id).get());
    }

    @Override
    public void delBadgeList(Long id) {
        badgeListRepository.deleteById(id);
    }

    @Override
    public List<BadgeList> findAllByUserId(Long userId) {
        return badgeListRepository.findAllByUserId(userId);
    }

    @Override
    public BadgeList addBadgeList(Goal goal) {
        log.info("addBadgeList");
        BadgeList badgeList = null;
        Badge badge = badgeService.isComplete(goal);
        log.info("badge : {}", badge.getId());
        if(badge != null) {
            badgeList = badgeListRepository.findByBadgeIdAndUserId(badge.getId(), goal.getUserId());
            if(badgeList == null) {
                badgeList = new BadgeList(badge, badge.getPoint(), 1, LocalDate.now(), goal.getUserId());
            } else {
                badgeList.setPoint(badgeList.getPoint() + badgeList.getPoint());
                badgeList.setCount(badgeList.getCount() + 1);
                badgeList.setDate(LocalDate.now());
            }
        }
        return badgeList;
    }

}
