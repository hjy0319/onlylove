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
public class TotalIncomeVO implements Serializable {

    private Integer totalIncome;

    private Integer totalFine;

    private Integer totalUsedMoney;

    private Integer restMoney;
}
