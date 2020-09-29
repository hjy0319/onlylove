package com.hujy.onlylove.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Task;
import com.hujy.onlylove.model.param.TaskPagingParam;
import com.hujy.onlylove.model.param.TaskSaveParam;
import com.hujy.onlylove.model.vo.WeekIncomeVO;
import com.hujy.onlylove.model.vo.RuleVO;
import com.hujy.onlylove.model.vo.TaskDetailVO;
import com.hujy.onlylove.model.vo.StatisticsVO;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 17:50
 */
public interface TaskService {

    void save(TaskSaveParam param);

    TaskDetailVO toEdit(String userCode);

    TaskDetailVO toDetail(String taskCode);

    Page<Task> taskPaging(TaskPagingParam param);

    StatisticsVO statistics(String userCode, String taskDate);

    WeekIncomeVO toIncome(String userCode, String taskDate);

    RuleVO toRule();

    void daySettlement(String userCode);

    void weekSettlement(String userCode);
}
