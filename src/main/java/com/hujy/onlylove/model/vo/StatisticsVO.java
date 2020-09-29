package com.hujy.onlylove.model.vo;

import com.hujy.onlylove.entity.Task;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-12 16:58
 */
@Data
public class StatisticsVO implements Serializable {

    private List<String> categories;

    private List<Task> currentWeekTask;

    private List<Task> lastWeekTask;

    private WeekIncomeVO weekIncome;

}
