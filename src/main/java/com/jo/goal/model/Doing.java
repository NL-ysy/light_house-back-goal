package com.jo.goal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Entity
@Data
@NoArgsConstructor
public class Doing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Goal goalId;
    private LocalDate checkDate;
    private String postTitle;
    private String postContent;
    private String postImg;

    public Doing(Long id, Goal goalId, LocalDate checkDate, String postTitle, String postContent, String postImg) {
        this.id = id;
        this.goalId = goalId;
        this.checkDate = checkDate;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImg = postImg;
    }
}
