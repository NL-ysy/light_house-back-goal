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
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "goal_id")
    private Goal goal;
//    @Column(unique = true)
    private LocalDate checkDate;
    private int week;
    private String postTitle;
    private String postContent;
    private String postImg;

    public Doing(Long id, Goal goal, LocalDate checkDate, int week, String postTitle, String postContent, String postImg) {
        this.id = id;
        this.goal = goal;
        this.checkDate = checkDate;
        this.week = week;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImg = postImg;
    }
}
