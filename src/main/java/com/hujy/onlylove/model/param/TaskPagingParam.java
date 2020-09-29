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
public class TaskPagingParam implements Serializable {

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 任务日期
     */
    private String taskDate;

    /**
     * 当前页
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 4;


}
