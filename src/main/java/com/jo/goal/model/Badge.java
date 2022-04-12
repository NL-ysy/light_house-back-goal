package com.jo.goal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String badgeName;
    private String badgeDesc;
    private String badgeImg;
    private int point;
    private String type;

    public Badge(String badgeName, String badgeDesc, String badgeImg, int point, String type) {
        this.badgeName = badgeName;
        this.badgeDesc = badgeDesc;
        this.badgeImg = badgeImg;
        this.point = point;
        this.type = type;
    }

}
