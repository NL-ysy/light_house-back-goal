package com.jo.goal.controller;

import com.jo.goal.model.BadgeList;
import com.jo.goal.service.BadgeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BadgeListController {

    private final BadgeListService badgeListService;

    @GetMapping("/mybadge")
    public List<BadgeList> findAll() {
        return badgeListService.findAll();
    }

    @GetMapping("/mybadge/{userId}")
    public List<BadgeList> findAllByUserId(@PathVariable Long userId) {
        return badgeListService.findByUserIdOrderByIdDesc(userId);
    }

    @GetMapping("/mybadge/list/{userId}")
    public List<BadgeList> findAllByTypeAndUserId(
            @RequestParam(value = "type", required = false) String type,
            @PathVariable Long userId
    ) {
        return badgeListService.findAllByTypeAndUserId(type, userId);
    }

}
