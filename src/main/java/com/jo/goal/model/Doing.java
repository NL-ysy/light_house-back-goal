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
    private LocalDate checkDate;
    private int week;
    private Long postId;

    public Doing(Long id, Goal goal, LocalDate checkDate, int week, Long postId) {
        this.id = id;
        this.goal = goal;
        this.checkDate = checkDate;
        this.week = week;
        this.postId = postId;
    }
}
