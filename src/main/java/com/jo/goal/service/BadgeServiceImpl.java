package com.jo.goal.service;

import com.jo.goal.model.Badge;
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

    @Transactional
    @Override
    public Badge addBadge(Badge badge) {
        log.info("add badge!");
        return badgeRepository.save(badge);
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
