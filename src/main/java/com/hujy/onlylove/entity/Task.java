package com.hujy.onlylove.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Task implements Serializable {
    private String taskCode;

    private String taskDate;

    private String userCode;

    private String yearWeek;

    private String weight;

    private Integer dietMealFlag;

    private Integer sportsFlag;

    private Integer grade;

    private Integer income;

    private Integer fine;

    private Integer taskStatus;

    private Date createTime;

    private Date lastModifyTime;

    private Integer isDel;

    private Integer weekDayNum;

    private static final long serialVersionUID = 1L;


}