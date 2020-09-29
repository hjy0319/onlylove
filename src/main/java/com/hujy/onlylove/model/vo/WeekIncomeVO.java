package com.hujy.onlylove.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-15 12:24
 */
@Data
public class WeekIncomeVO implements Serializable {

    private Integer weekIncome;

    private Integer persevereReward;

    private Integer progressReward;

    private Integer weekFine;

    private Integer netIncome;
}
