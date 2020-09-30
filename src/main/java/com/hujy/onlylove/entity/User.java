package com.hujy.onlylove.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;

    private String nickName;

    private String province;

    private String city;

    private String avatarUrl;

    private String mailAccount;

    private Date createTime;

    private Date lastModifyTime;

    private Integer isDel;

}