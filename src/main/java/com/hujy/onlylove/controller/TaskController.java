package com.hujy.onlylove.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Task;
import com.hujy.onlylove.model.param.TaskPagingParam;
import com.hujy.onlylove.model.vo.*;
import com.hujy.onlylove.model.param.TaskSaveParam;
import com.hujy.onlylove.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-08 17:48
 */
@RestController
@RequestMapping("task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/save")
    public RespVO<String> save(@RequestBody TaskSaveParam param) {
        taskService.save(param);
        return new RespVO<>("ok");
    }

    @GetMapping("/toEdit")
    public RespVO<TaskDetailVO> toEdit(@RequestParam String userCode) {
        TaskDetailVO taskDetailVO = taskService.toEdit(userCode);
        return new RespVO<>(taskDetailVO);
    }

    @GetMapping("/toDetail")
    public RespVO<TaskDetailVO> toDetail(@RequestParam String taskCode) {
        TaskDetailVO taskDetailVO = taskService.toDetail(taskCode);
        return new RespVO<>(taskDetailVO);
    }

    @PostMapping("/taskPaging")
    public RespVO<Page<Task>> taskPaging(@RequestBody TaskPagingParam param) {
        Page<Task> taskPage = taskService.taskPaging(param);
        return new RespVO<>(taskPage);
    }

    @GetMapping("/statistics")
    public RespVO<StatisticsVO> statistics(@RequestParam String userCode, String taskDate) {
        StatisticsVO weekWeight = taskService.statistics(userCode, taskDate);
        return new RespVO<>(weekWeight);
    }

    @GetMapping("/toIncome")
    public RespVO<WeekIncomeVO> toIncome(@RequestParam String userCode, String taskDate) {
        WeekIncomeVO vo = taskService.toIncome(userCode, taskDate);
        return new RespVO<>(vo);
    }

    @GetMapping("/toRule")
    public RespVO<RuleVO> toRule() {
        RuleVO ruleVO = taskService.toRule();
        return new RespVO<>(ruleVO);
    }




}
