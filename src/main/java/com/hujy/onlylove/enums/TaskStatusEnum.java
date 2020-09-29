package com.hujy.onlylove.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-17 10:37
 */
@Getter
@AllArgsConstructor
public enum  TaskStatusEnum {

    OFF_NORMAL(0, "异常"),

    NORMAL(1, "正常");

    private Integer code;

    private String name;

}
