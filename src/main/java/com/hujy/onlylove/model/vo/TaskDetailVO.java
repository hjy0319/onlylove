package com.hujy.onlylove.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 19:27
 */
@Data
public class TaskDetailVO implements Serializable {
    private String taskCode;

    private String taskDate;

    private String userCode;

    private String yearWeek;

    private String weight;

    private String compareWeight;

    private Integer dietMealFlag;

    private Integer sportsFlag;

    private Integer grade;

    private Integer income;

    private Integer fine;

    private Integer taskStatus;

    private Integer weekDayNo;

    private Boolean dietMealDay;

    private Boolean sportsDay;




}
