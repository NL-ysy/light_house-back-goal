package com.jo.goal.controller;

import com.jo.goal.model.Doing;
import com.jo.goal.service.DoingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api")
public class DoingController {
    private DoingService doingService;

    @PostMapping("/doing")
    public void add(@RequestBody Doing doing) {
        doingService.addDoing(doing);
    }

    @PutMapping("/doing")
    public void edit(@RequestBody Doing doing) {doingService.editDoing(doing);}

    @GetMapping("/doing")
    public List<Doing> getAll(){return doingService.getAllDoing();}

    @GetMapping("/doing/{id}")
    public Optional<Doing> getGoalById(@PathVariable Long id) {return doingService.getDoingById(id);}

    @DeleteMapping("/doing/{id}")
    public void del(@PathVariable("id") Long id) {doingService.delDoing(id);}
}