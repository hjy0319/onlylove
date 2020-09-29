package com.hujy.onlylove.config;

import com.hujy.onlylove.mapper.ConfigMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 15:15
 */
@Component
public class BusinessConfig {

    public static final String DAY_REWARD_SIGN_IN = "day_reward:sign_in";
    public static final String DAY_REWARD_DIET_MEAL = "day_reward:diet_meal";
    public static final String DAY_REWARD_SPORTS = "day_reward:sports";

    public static final String DAY_FINE_SIGN_IN = "day_reward:sign_in";
    public static final String DAY_FINE_DIET_MEAL = "day_reward:diet_meal";
    public static final String DAY_FINE_SPORTS = "day_reward:sports";

    public static final String DAY_GRADE_SIGN_IN = "day_grade:sign_in";
    public static final String DAY_GRADE_DIET_MEAL = "day_grade:diet_meal";
    public static final String DAY_GRADE_SPORTS = "day_grade:sports";

    public static final String WEEK_REWARD_PERSEVERE = "week_reward:persevere";
    public static final String WEEK_REWARD_PROGRESS = "week_reward:progress";

    public static final String FIXED_DAY_DIET_MEAL = "fixed_day:diet_meal";
    public static final String FIXED_DAY_SPORTS = "fixed_day:sports";

    public static final String TARGET_PROGRESS = "target:progress";





    @Resource
    private ConfigMapper configMapper;

    public String get(String configKey) {
        return configMapper.get(configKey);
    }

}
