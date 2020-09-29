package com.hujy.onlylove.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Reward implements Serializable {
    private String yearWeek;

    private String userCode;

    private Integer rewardType;

    private Integer money;

    private Date createTime;

    private Date lastModifyTime;

    private Integer isDel;

    private static final long serialVersionUID = 1L;

}