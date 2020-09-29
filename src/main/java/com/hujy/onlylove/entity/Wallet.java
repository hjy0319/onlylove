package com.hujy.onlylove.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Wallet implements Serializable {
    private String userCode;

    private Integer money;

    private Date createTime;

    private Date lastModifyTime;

    private Integer isDel;

}