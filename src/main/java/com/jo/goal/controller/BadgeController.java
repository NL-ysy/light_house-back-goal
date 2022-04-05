package com.jo.goal.controller;

import com.jo.goal.model.Badge;
import com.jo.goal.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BadgeController {

    private final BadgeService badgeService;

    @PostMapping("/badge")
    public void add(@RequestBody Badge badge){badgeService.addBadge(badge);}

    @PutMapping("/badge")
    public void edit(@RequestBody Badge badge){badgeService.editBadge(badge);}

    @GetMapping("/badge")
    public List<Badge> getAll(){return badgeService.getAllBadge();}

    @GetMapping("/badge/{id}")
    public Optional<Badge> getBadgeById(@PathVariable Long id) {return badgeService.getBadgeById(id);}

    @DeleteMapping("/badge/{id}")
    public void del(@PathVariable("id") Long id) {badgeService.delBadge(id);}

    @GetMapping("/badge/list")
    public List<Badge> findAllByType(@RequestParam(value = "type", required = false) String type) {
        return badgeService.findAllByType(type);
    }
}
