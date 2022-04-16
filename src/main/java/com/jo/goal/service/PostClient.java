package com.jo.goal.service;

import com.jo.goal.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "PostClient", url = "http://localhost:8081")
//@FeignClient(name = "feignClient", url = "http://localhost:8081")
public interface PostClient {
    @GetMapping("/api/post/{id}")
    Post findById(@PathVariable("id") Long id);

    @GetMapping("/api/post/auth/{goalId}")
    List<Post> findAllByGoalId(@PathVariable("goalId") Long goalId);

}
