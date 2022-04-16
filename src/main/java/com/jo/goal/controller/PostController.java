package com.jo.goal.controller;

import com.jo.goal.model.Post;
import com.jo.goal.service.PostClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostClient postClient;

    @PostMapping("/feign")
    public Post feignTest() {
        System.out.println("###restTempleate 시작됨 ###");
        Post post = postClient.findById(1L); //외부 API 호출
        System.out.println("Feign을 이용한 호출 결과 : " + post.toString());
        return post;
    }

//    @PostMapping("/restTemplate")
//    public Post restTempleate() {
//        System.out.println("### restTempleate 시작됨 ###");
//        RestTemplate restTemplate = new RestTemplate();
//        Post post = restTemplate.getForObject("http://localhost:8080/posts/2", Post.class); //외부 API 호출
//        System.out.println("RestTemplate을 이용한 호출 결과 : " + post.toString());
//        return post;
//    }

    //가상의 외부 API
    @GetMapping("/posts/{id}")
    public Post getPostId(@PathVariable("id") long id) {
        System.out.println("### getPostId가 시작됨 ###");
        Post post = new Post();
        post.setContent("내용");
        post.setId(3L);
        post.setCategoryId("테스트");
        post.setTitle("제목");
        return post;
    }
}
