package com.hujy.onlylove.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-17 10:37
 */
@Getter
@AllArgsConstructor
public enum BillTypeEnum {

    INCOME(1, "收入"),

    FINE(2, "罚款"),

    CASH(3, "提现");

    private Integer code;

    private String name;

    public static final Map<Integer, String> lookup = new HashMap<>(8);

    static {
        for (BillTypeEnum e : EnumSet.allOf(BillTypeEnum.class)) {
            lookup.put(e.getCode(), e.getName());
        }
    }
}
