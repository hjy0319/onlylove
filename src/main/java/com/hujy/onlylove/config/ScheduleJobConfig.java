package com.hujy.onlylove.config;

import com.hujy.onlylove.entity.User;
import com.hujy.onlylove.mapper.UserMapper;
import com.hujy.onlylove.service.TaskService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-16 15:09
 */
@Component
@Configuration
@EnableScheduling
public class ScheduleJobConfig {

    @Resource
    private TaskService taskService;

    @Resource
    private UserMapper userMapper;

    @Scheduled(cron = "0 10 0 * * ?")
    private void doDaySettlement() {
        List<User> users = userMapper.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return;
        }

        for (User user : users) {
            taskService.daySettlement(String.valueOf(user.getId()));
        }

    }

    @Scheduled(cron = "0 30 0 ? * MON")
    private void doWeekSettlement() {
        List<User> users = userMapper.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return;
        }
        for (User user : users) {
            taskService.weekSettlement(String.valueOf(user.getId()));

        }
    }

    @Scheduled(cron = "0 0 10 * * ?")
    private void doSendSignInRemindMail() {
        List<User> users = userMapper.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return;
        }
        for (User user : users) {
            taskService.sendSignInRemindMail(user);
        }
    }

    @Scheduled(cron = "0 0 22 * * ?")
    private void doSendFineRemindMail() {
        List<User> users = userMapper.getAll();
        if (CollectionUtils.isEmpty(users)) {
            return;
        }
        for (User user : users) {
            taskService.sendFineRemindMail(user);
        }
    }
}
