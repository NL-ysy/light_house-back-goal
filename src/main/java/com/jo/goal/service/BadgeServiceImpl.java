package com.jo.goal.service;

import com.jo.goal.model.Badge;
import com.jo.goal.model.Goal;
import com.jo.goal.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    @Override
    public Badge addBadge(Badge badge) {
        log.info("add badge!");
        return badgeRepository.save(badge);
    }

    @Override
    public Badge editBadge(Badge badge) {
        log.info("edit badge {}!", badgeRepository.findById(badge.getId()).get());
        return badgeRepository.save(badge);
    }

    @Override
    public List<Badge> getAllBadge() {
        log.info("get All badge!");
        return badgeRepository.findAll();
    }

    @Override
    public Optional<Badge> getBadgeById(Long id) {
        log.info("get badge by badge id {}!", id);
        return Optional.ofNullable(badgeRepository.findById(id).get());
    }

    @Override
    public void delBadge(Long id) {
        log.info("del badge by id {}!", id);
        badgeRepository.deleteById(id);
    }

    @Override
    public Optional<Badge> findByBadgeName(String badgeName) {
        return Optional.ofNullable(findByBadgeName(badgeName).get());
    }

    @Override
    public Badge isComplete(Goal goal) { // 목표를 달성 여부 파악하고 보상되는 배지 리턴
        Badge badge = null;
        log.info("goal id : {}", goal.getId());
        log.info("goal totalCount : {}", goal.getTotalCount());
        log.info("goal count : {}", goal.getCount());
        double result = (goal.getCount() * 1.0) / (goal.getTotalCount() * 1.0);
        log.info("badge : {}", result);

        if(result * 100 == 100) { // 100% 달성
            log.info("100");
            badge = badgeRepository.findByBadgeName("100").get();
        } else if(result * 100 >= 90) { // 90% 달성
            log.info("90");
            badge = badgeRepository.findByBadgeName("90").get();
        } else if(result * 100 >= 80) { // 80% 달성
            log.info("80");
            badge = badgeRepository.findByBadgeName("80").get();
        } else {
            log.error("fail");
        }

        return badge;
    }

    @Override
    public Badge createFirstGoal() { // 처음 생성한 목표에 대한 기념 배지
        return badgeRepository.findByBadgeName("Set First Goal").get();
    }

    @Override
    public Badge achieveFirstGoal() { // 처음 달성한 목표에 대한 기념 배지
        return badgeRepository.findByBadgeName("Achieve a First Goal").get();
    }
}
