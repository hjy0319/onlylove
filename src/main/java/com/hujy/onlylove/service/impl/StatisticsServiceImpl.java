package com.hujy.onlylove.service.impl;

import com.hujy.onlylove.config.BusinessConfig;
import com.hujy.onlylove.entity.Task;
import com.hujy.onlylove.mapper.TaskMapper;
import com.hujy.onlylove.model.vo.TrendVO;
import com.hujy.onlylove.service.StatisticsService;
import com.hujy.onlylove.util.MyDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-11-10 13:58
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private BusinessConfig businessConfig;

    @Resource
    private TaskMapper taskMapper;
    @Override
    public TrendVO trend(String userCode, String startDate, String endDate) {

        int dayNum = Integer.parseInt(businessConfig.get(BusinessConfig.DAY_NUM_TREND));

        if (StringUtils.isBlank(endDate)) {
            endDate = MyDateUtils.getToday();
        }
        if (StringUtils.isBlank(startDate)) {
            startDate = MyDateUtils.getPastDate(endDate, dayNum);
        }

        List<Task> tasks = taskMapper.getRangeTasks(userCode, startDate, endDate);
        if (CollectionUtils.isEmpty(tasks)) {
            return new TrendVO();
        }
        TrendVO trend = getTrend(tasks, dayNum);
        startDate = MyDateUtils.dateStrFormat(startDate, "M.d");
        endDate = MyDateUtils.dateStrFormat(endDate, "M.d");
        trend.setTitle(startDate + " ~ " + endDate);

        return trend;
    }

    private TrendVO getTrend(List<Task> tasks, int dayNum) {

        TrendVO vo = new TrendVO();
        List<Task> fixedTasks = getFixedTasks(tasks, dayNum);
        List<String> x = new ArrayList<>();
        List<String> y = new ArrayList<>();
        for (Task fixedTask : fixedTasks) {
            x.add(MyDateUtils.dateStrFormat(fixedTask.getTaskDate(), "M.d"));
            y.add(fixedTask.getWeight());
        }

        vo.setX(x);
        vo.setY(y);

        return vo;
    }

    private List<Task> getFixedTasks(List<Task> tasks, int dayNum) {
        int amount = tasks.size();
        if (amount <= dayNum) {
            return tasks;
        }

        float offset = (float) amount /(float) dayNum;
        List<Task> fixedTasks = new ArrayList<>();
        for (int i = 0; i < dayNum; i++) {
            if (i == 0) {
                fixedTasks.add(tasks.get(0));
                continue;
            }

            if (i == dayNum - 1) {
                fixedTasks.add(tasks.get(amount - 1));
                continue;
            }

            int round = Math.round(i * offset);
            fixedTasks.add(tasks.get(round));
        }
        return fixedTasks;
    }

}
