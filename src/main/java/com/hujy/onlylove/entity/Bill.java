package com.hujy.onlylove.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Bill implements Serializable {
    private Integer id;

    private String userCode;

    private String yearWeek;

    private Integer billType;

    private Integer money;

    private Date createTime;

    private Date lastModifyTime;

    private Integer isDel;

    private String createTimeStr;

}