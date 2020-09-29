package com.hujy.onlylove.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-15 17:02
 */
@Data
public class RuleVO implements Serializable {

    private String dayRewardSignIn;
    private String dayRewardDietMeal;
    private String dayRewardSports;

    private String dayFineSignIn;
    private String dayFineDietMeal;
    private String dayFineSports;

    private String fixedDayDietMeal;
    private String fixedDaySports;

    private String weekRewardPersevere;
    private String weekRewardProgress;


}
