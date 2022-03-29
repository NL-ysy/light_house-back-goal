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
    private int period;
    private int weekCount;
    private int totalCount;
    private int count;
    private boolean state = true;
    private boolean result;


    @Builder
    public Goal(Long id, String goalTitle, String goalDesc, LocalDate startDay, LocalDate endDay, int period, int weekCount, int totalCount, int count, boolean state, boolean result) {
        this.id = id;
        this.goalTitle = goalTitle;
        this.goalDesc = goalDesc;
        this.startDay = startDay;
        this.endDay = endDay;
        this.period = period;
        this.weekCount = weekCount;
        this.totalCount = totalCount;
        this.count = count;
        this.state = state;
        this.result = result;
    }

    public boolean getState() {
        return true;
    }

}