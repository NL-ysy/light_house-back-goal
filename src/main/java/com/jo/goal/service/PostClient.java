package com.jo.goal.service;

import com.jo.goal.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "feignClient", url = "http://localhost:8081")
public interface PostClient {
    @GetMapping("/api/post/{id}")
    Post findById(@PathVariable("id") Long id);
}
