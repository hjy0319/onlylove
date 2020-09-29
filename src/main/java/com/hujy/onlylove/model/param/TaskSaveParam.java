package com.hujy.onlylove.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-08 17:52
 */
@Data
public class TaskSaveParam implements Serializable {

    /**
     * 任务编号
     */
    private String taskCode;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 今日体重
     */
    private String weight;

    /**
     * 是否吃代餐标识
     */
    private Integer dietMealFlag;

    /**
     * 是否运动标识
     */
    private Integer sportsFlag;


}
