package com.hujy.onlylove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Task;
import com.hujy.onlylove.model.param.TaskPagingParam;
import com.hujy.onlylove.model.vo.WeekIncomeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 15:17
 */
public interface TaskMapper extends BaseMapper<Task> {

    Integer save(@Param("task") Task task);

    Task getByTaskDateAndUserCode(@Param("taskDate") String taskDate, @Param("userCode") String userCode);

    Task getByTaskCode(@Param("taskCode") String taskCode);

    Page<Task> taskPaging(Page page, @Param("param") TaskPagingParam param);

    List<Task> getWeekTasks(@Param("userCode") String userCode, @Param("yearWeek") String yearWeek);

    WeekIncomeVO getWeekIncome(@Param("userCode") String userCode, @Param("yearWeek") String yearWeek);

    String getWeekFinalWeight(@Param("userCode") String userCode, @Param("yearWeek") String yearWeek);
}
