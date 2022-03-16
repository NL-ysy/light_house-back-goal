package com.jo.goal.model;

import com.jo.goal.base.UtilTimeSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

}
