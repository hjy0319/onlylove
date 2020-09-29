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
public enum WeekDayEnum {

    MON(1, "(一)"),

    TUES(2, "(二)"),

    WED(3, "(三)"),

    THUR(4, "(四)"),

    FRI(5, "(五)"),

    SAT(6, "(六)"),

    SUN(7, "(日)");

    private Integer code;

    private String name;

    public static final Map<Integer, String> lookup = new HashMap<>();

    static {
        for (WeekDayEnum e : EnumSet.allOf(WeekDayEnum.class)) {
            lookup.put(e.getCode(), e.getName());
        }
    }
}
