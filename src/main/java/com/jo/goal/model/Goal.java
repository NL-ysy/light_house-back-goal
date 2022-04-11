package com.jo.goal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private int state = 0;
    private boolean result;
    @JsonIgnore // 응답값에 보이지 않게 숨기기
    @OneToMany(mappedBy = "goal", cascade = CascadeType.REMOVE)
    private List<Doing> doing = new ArrayList<>();
    private Long userId = 1L;

    @Builder
    public Goal(Long id, String goalTitle, String goalDesc, LocalDate startDay, LocalDate endDay, int period, int weekCount, int totalCount, int count, int state, boolean result, Long userId) {
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
        this.userId = userId;
    }

    public Goal(String goalTitle, String goalDesc, LocalDate startDay, LocalDate endDay, int period, int weekCount, int totalCount, Long userId) {
        this.goalTitle = goalTitle;
        this.goalDesc = goalDesc;
        this.startDay = startDay;
        this.endDay = endDay;
        this.period = period;
        this.weekCount = weekCount;
        this.totalCount = totalCount;
        this.userId = userId;
    }

    // api test용 생성자 - count 설정 임의로 가능
    public Goal(String goalTitle, String goalDesc, LocalDate startDay, LocalDate endDay, int period, int weekCount, int totalCount, int count, Long userId) {
        this.goalTitle = goalTitle;
        this.goalDesc = goalDesc;
        this.startDay = startDay;
        this.endDay = endDay;
        this.period = period;
        this.weekCount = weekCount;
        this.totalCount = totalCount;
        this.count = count;
        this.userId = userId;
    }
}