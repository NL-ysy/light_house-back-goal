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
    public List<BadgeList> findByUserIdOrderByIdDesc(Long userId) {
        return badgeListRepository.findByUserIdOrderByIdDesc(userId);
    }

    @Override
    public BadgeList findByBadgeIdAndUserId(Long badgeId, Long userId) {
        return badgeListRepository.findByBadgeIdAndUserId(badgeId, userId);
    }

    @Override
    public List<BadgeList> findAllByTypeAndUserId(String type, Long userId) {
        return badgeListRepository.findAllByTypeAndUserId(type, userId);
    }
}
