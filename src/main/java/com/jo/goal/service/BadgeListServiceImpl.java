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
    public BadgeList addBadgeList(Goal goal) { // 목표를 80% 이상 달성한 경우 사용자의 배지리스트에 배지 추가
        log.info("addBadgeList");
        BadgeList badgeList = null;

        Badge badge = badgeService.isComplete(goal); // 목표 달성하면 배지 부여
        log.info("badge : {}", badge.getId());
        if(badge != null) {
            badgeList = badgeListRepository.findByBadgeIdAndUserId(badge.getId(), goal.getUserId());
            if(badgeList == null) { // 배지리스트에 같은 배지가 존재하지 않는 경우
                badgeList = new BadgeList(badge, badge.getPoint(), 1, LocalDate.now(), badge.getType(), goal.getUserId());
            } else { // 배지리스트에 동일한 배지가 존재하는 경우 - 포인트, 갯수 증가, 날짜 업데이트
                badgeList.setPoint(badgeList.getPoint() + badgeList.getPoint());
                badgeList.setCount(badgeList.getCount() + 1);
                badgeList.setDate(LocalDate.now());
            }
        }
        return badgeList;
    }

    @Override
    public List<BadgeList> findAllByTypeAndUserId(String type, Long userId) {
        return badgeListRepository.findAllByTypeAndUserId(type, userId);
    }
}
