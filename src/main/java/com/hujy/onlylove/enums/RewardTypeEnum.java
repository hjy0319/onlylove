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
public enum RewardTypeEnum {

    PERSEVERE_REWARD(1, "进步奖"),

    PROGRESS_REWARD(2, "全勤奖");

    private Integer code;

    private String name;

}
