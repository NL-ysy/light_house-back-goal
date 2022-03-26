package com.jo.goal.model;

import com.jo.goal.base.UtilTimeSetter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
    private Date startDay;
    private Date endDay;
    private int weekCount;
    private int totalCount;
    private int count;
    private int doing;
    private int state;

    @Builder
    public Goal(Long id, String goalTitle, String goalDesc, Date startDay, Date endDay, int weekCount, int totalCount, int count, int doing, int state) {
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