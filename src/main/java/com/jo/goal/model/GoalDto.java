package com.jo.goal.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalDto {

    private Long id;
    private String goalTitle;
    private String goalDesc;
    private LocalDate startDay;
    private LocalDate endDay;
    private int weekCount;
    private int totalCount;
    private int count;
    private int doing;
    private int state;

}
