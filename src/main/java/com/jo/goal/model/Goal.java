package com.jo.goal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@Entity
public class Goal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goalTitle;
    private String goalDesc;
    private LocalDate startDay;
    private LocalDate endDay;
    private int weekCount;
    private int totalCount;
    private int count = 0;
    private int doing = 0;
    private int state = 0;

    @Builder
    public Goal(Long id, String goalTitle, String goalDesc, LocalDate startDay, LocalDate endDay, int weekCount, int totalCount, int count, int doing, int state) {
        this.id = id;
        this.goalTitle = goalTitle;
        this.goalDesc = goalDesc;
        this.startDay = startDay;
        this.endDay = endDay;
        this.weekCount = weekCount;
        this.totalCount = totalCount;
        this.count = count;
        this.doing = doing;
        this.state = state;
    }
}
