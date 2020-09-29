package com.hujy.onlylove.util;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 11:14
 */
public class KeyGenerator {

    private static final String TASK_CODE_PREFIX = "T";

    public static String getTaskCode() {
        return TASK_CODE_PREFIX + System.currentTimeMillis();
    }

    public static void main(String[] args) {
        System.out.println(getTaskCode());
    }
}
