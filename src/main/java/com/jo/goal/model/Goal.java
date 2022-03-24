package com.jo.goal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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

    public int isEnd(LocalDate endDay) {
        LocalDate today = LocalDate.now();
        state = 0;

        if(endDay.isAfter(today) || endDay.isEqual(today)) {
            state = 0;
        } else if(endDay.isBefore(today)) {
            state = 1;
        }
        return state;
    }

}
