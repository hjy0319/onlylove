package com.hujy.onlylove.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.config.BusinessConfig;
import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.entity.Reward;
import com.hujy.onlylove.entity.Task;
import com.hujy.onlylove.entity.User;
import com.hujy.onlylove.enums.BillTypeEnum;
import com.hujy.onlylove.enums.RewardTypeEnum;
import com.hujy.onlylove.enums.TaskStatusEnum;
import com.hujy.onlylove.enums.WeekDayEnum;
import com.hujy.onlylove.mapper.TaskMapper;
import com.hujy.onlylove.model.param.TaskPagingParam;
import com.hujy.onlylove.model.param.TaskSaveParam;
import com.hujy.onlylove.model.vo.RuleVO;
import com.hujy.onlylove.model.vo.StatisticsVO;
import com.hujy.onlylove.model.vo.TaskDetailVO;
import com.hujy.onlylove.model.vo.WeekIncomeVO;
import com.hujy.onlylove.service.TaskService;
import com.hujy.onlylove.service.TransactionalService;
import com.hujy.onlylove.util.KeyGenerator;
import com.hujy.onlylove.util.MailUtils;
import com.hujy.onlylove.util.MyDateUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 10:38
 */
@Component
public class TaskServiceImpl implements TaskService {

    @Resource
    private BusinessConfig businessConfig;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TransactionalService transactionalService;

    /**
     * 保存任务
     *
     * @param param
     * @return void
     * @author hujy
     * @date 2020-09-10 10:28
     */
    @Override
    public void save(TaskSaveParam param) {
        if (param == null) {
            throw new RuntimeException("参数错误");
        }

        String taskCode = param.getTaskCode();

        if (StringUtils.isBlank(taskCode)) {
            taskCode = KeyGenerator.getTaskCode();
        }

        Date now = new Date();
        Task task = new Task();
        task.setTaskCode(taskCode);
        String taskDate = MyDateUtils.dateToDateStr(now, MyDateUtils.NORMAL_DATE_FORMAT);
        task.setTaskDate(taskDate);

        String yearWeek = MyDateUtils.getYearWeek(now);
        task.setYearWeek(yearWeek);

        task.setUserCode(param.getUserCode());
        task.setWeight(param.getWeight());

        Integer dietMealFlag = param.getDietMealFlag() == null ? 0 : param.getDietMealFlag();
        Integer sportsFlag = param.getSportsFlag() == null ? 0 : param.getSportsFlag();
        task.setDietMealFlag(dietMealFlag);
        task.setSportsFlag(sportsFlag);

        int weekDayNum = MyDateUtils.getWeekDayNum(MyDateUtils.dateStrToDate(taskDate, MyDateUtils.NORMAL_DATE_FORMAT));
        boolean isDietMealDay = checkIsFixedDay(weekDayNum, BusinessConfig.FIXED_DAY_DIET_MEAL);
        boolean isSportsDay = checkIsFixedDay(weekDayNum, BusinessConfig.FIXED_DAY_SPORTS);

        int income = getIncome(dietMealFlag, sportsFlag);
        task.setIncome(income);
        int fine = getFine(dietMealFlag, sportsFlag, isDietMealDay, isSportsDay);
        task.setFine(fine);
        task.setGrade(getGrade(income, fine));
        task.setTaskStatus(TaskStatusEnum.NORMAL.getCode());

        taskMapper.save(task);

    }

    private Integer getGrade(Integer income, Integer fine) {
        int grade = 0;
        int netIncome = income - fine;
        if (fine == 0) {
            if (netIncome > 0 && netIncome < 5) {
                grade = 3;
            } else if (netIncome > 5 && netIncome < 15) {
                grade = 4;
            } else if (netIncome >= 15) {
                grade = 5;
            }
        } else {
            if (netIncome > 0 && netIncome < 5) {
                grade = 1;
            } else if (netIncome >= 5 && netIncome < 10) {
                grade = 2;
            } else if (netIncome > 10) {
                grade = 3;
            }
        }

        return grade;
    }

    private int getIncome(Integer dietMealFlag, Integer sportsFlag) {
        int income = 0;
        String v = businessConfig.get(BusinessConfig.DAY_REWARD_SIGN_IN);
        income += Integer.valueOf(v);

        if (dietMealFlag != null && dietMealFlag == 1) {
            String value = businessConfig.get(BusinessConfig.DAY_REWARD_DIET_MEAL);
            income += Integer.valueOf(value);
        }

        if (sportsFlag != null && sportsFlag == 1) {
            String value = businessConfig.get(BusinessConfig.DAY_REWARD_SPORTS);
            income += Integer.valueOf(value);
        }

        return income;
    }

    private int getFine(Integer dietMealFlag, Integer sportsFlag, boolean isDietMealDay, boolean isSportsDay) {
        int fine = 0;

        if (isDietMealDay && (dietMealFlag == null || dietMealFlag == 0)) {
            String v = businessConfig.get(BusinessConfig.DAY_FINE_DIET_MEAL);
            fine += Integer.valueOf(v);
        }

        if (isSportsDay && (sportsFlag == null || sportsFlag == 0)) {
            String v = businessConfig.get(BusinessConfig.DAY_FINE_SPORTS);
            fine += Integer.valueOf(v);
        }

        return fine;
    }

    /**
     * 获取编辑页内容
     *
     * @param userCode
     * @return com.hujy.onlylove.model.vo.TaskDetailVO
     * @author hujy
     * @date 2020-09-10 10:29
     */
    @Override
    public TaskDetailVO toEdit(String userCode) {
        if (StringUtils.isBlank(userCode)) {
            throw new RuntimeException("用户编号为空");
        }
        String taskDate = MyDateUtils.dateToDateStr(new Date(), MyDateUtils.NORMAL_DATE_FORMAT);
        TaskDetailVO vo = new TaskDetailVO();


        Task taskDetail = taskMapper.getByTaskDateAndUserCode(taskDate, userCode);
        if (taskDetail != null) {
            BeanUtils.copyProperties(taskDetail, vo);
        }


        int weekDayNo = MyDateUtils.getWeekDayNum(MyDateUtils.dateStrToDate(taskDate, MyDateUtils.NORMAL_DATE_FORMAT));
        vo.setWeekDayNo(weekDayNo);
        vo.setDietMealDay(checkIsFixedDay(weekDayNo, BusinessConfig.FIXED_DAY_DIET_MEAL));
        vo.setSportsDay(checkIsFixedDay(weekDayNo, BusinessConfig.FIXED_DAY_SPORTS));
        return vo;
    }

    private boolean checkIsFixedDay(int weekDayNo, String businessKey) {
        String value = businessConfig.get(businessKey);
        if (StringUtils.isBlank(value)) {
            return false;
        }
        List values = Arrays.asList(value.split(","));
        return values.contains(String.valueOf(weekDayNo));
    }

    @Override
    public TaskDetailVO toDetail(String taskCode) {
        Task task = taskMapper.getByTaskCode(taskCode);
        if (task == null) {
            return null;
        }

        TaskDetailVO vo = new TaskDetailVO();
        BeanUtils.copyProperties(task, vo);

        if (StringUtils.isNotBlank(task.getWeight())) {
            String yesterday = MyDateUtils.getYesterday(task.getTaskDate());
            Task yesterdayTask = taskMapper.getByTaskDateAndUserCode(yesterday, task.getUserCode());
            if (yesterdayTask != null && StringUtils.isNotBlank(yesterdayTask.getWeight())) {
                BigDecimal todayWeight = new BigDecimal(task.getWeight()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal yesterdayWeight = new BigDecimal(yesterdayTask.getWeight()).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal compareWeight = todayWeight.subtract(yesterdayWeight);
                vo.setCompareWeight(compareWeight.toString());

            }
        }


        int weekDayNo = MyDateUtils.getWeekDayNum(MyDateUtils.dateStrToDate(task.getTaskDate(), MyDateUtils.NORMAL_DATE_FORMAT));
        vo.setWeekDayNo(weekDayNo);
        vo.setDietMealDay(checkIsFixedDay(weekDayNo, BusinessConfig.FIXED_DAY_DIET_MEAL));
        vo.setSportsDay(checkIsFixedDay(weekDayNo, BusinessConfig.FIXED_DAY_SPORTS));

        return vo;
    }

    @Override
    public Page<Task> taskPaging(TaskPagingParam param) {
        Page pageCondition = new Page(param.getPageNum(), param.getPageSize(), true);
        Page<Task> pageResult = taskMapper.taskPaging(pageCondition, param);
        if (pageResult != null) {
            List<Task> records = pageResult.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                for (Task record : records) {
                    record.setIncome(record.getIncome() - record.getFine());
                    int weekDayNum = MyDateUtils.getWeekDayNum(MyDateUtils.dateStrToDate(record.getTaskDate(), MyDateUtils.NORMAL_DATE_FORMAT));
                    record.setWeekDayNum(weekDayNum);
                }
            }
        }

        return pageResult;
    }

    @Override
    public StatisticsVO statistics(String userCode, String taskDate) {
        if (StringUtils.isBlank(userCode)) {
            throw new RuntimeException("参数错误");
        }


        Date date = StringUtils.isBlank(taskDate) ? new Date() : MyDateUtils.dateStrToDate(taskDate, MyDateUtils.NORMAL_DATE_FORMAT);
        String currentWeek = MyDateUtils.getYearWeek(date);
        String lastWeek = MyDateUtils.getYearWeek(MyDateUtils.getPastDate(date, 7));

        StatisticsVO vo = new StatisticsVO();
        vo.setCategories(getCategories(date));
        List<Task> currentWeekTask = taskMapper.getWeekTasks(userCode, currentWeek);
        List<Task> lastWeekTask = taskMapper.getWeekTasks(userCode, lastWeek);
        vo.setCurrentWeekTask(currentWeekTask);
        vo.setLastWeekTask(lastWeekTask);

        WeekIncomeVO weekIncome = taskMapper.getWeekIncome(userCode, currentWeek);
        weekIncome = Optional.ofNullable(weekIncome).orElse(new WeekIncomeVO());
        weekIncome.setWeekIncome(Optional.ofNullable(weekIncome.getWeekIncome()).orElse(0));
        weekIncome.setWeekFine(Optional.ofNullable(weekIncome.getWeekFine()).orElse(0));
        weekIncome.setPersevereReward(Optional.ofNullable(weekIncome.getPersevereReward()).orElse(0));
        weekIncome.setProgressReward(Optional.ofNullable(weekIncome.getProgressReward()).orElse(0));

        vo.setWeekIncome(weekIncome);

        return vo;

    }


    private static List<String> getCategories(Date date) {

        List<String> categories = new ArrayList<>(7);
        int weekDayNum = MyDateUtils.getWeekDayNum(date);
        weekDayNum = weekDayNum == 0 ? 7 : weekDayNum;

        for (int i = 1; i <= 7; i++) {

            Date pastDate = MyDateUtils.getPastDate(date, weekDayNum - i);
            String dateStr = MyDateUtils.dateToDateStr(pastDate, "d");
            System.out.println(dateStr + WeekDayEnum.lookup.get(i));
            categories.add(dateStr + WeekDayEnum.lookup.get(i));

        }
        return categories;
    }

    @Override
    public WeekIncomeVO toIncome(String userCode, String taskDate) {
        if (StringUtils.isBlank(userCode)) {
            throw new RuntimeException("参数错误");
        }
        Date date = StringUtils.isBlank(taskDate) ? new Date() : MyDateUtils.dateStrToDate(taskDate, MyDateUtils.NORMAL_DATE_FORMAT);
        String yearWeek = MyDateUtils.getYearWeek(date);

        WeekIncomeVO vo = taskMapper.getWeekIncome(userCode, yearWeek);
        vo = vo == null ? new WeekIncomeVO() : vo;


        Integer weekIncome = vo.getWeekIncome() == null ? 0 : vo.getWeekIncome();
        Integer weekFine = vo.getWeekFine() == null ? 0 : vo.getWeekFine();
        Integer persevereReward = vo.getPersevereReward() == null ? 0 : vo.getPersevereReward();
        Integer progressReward = vo.getProgressReward() == null ? 0 : vo.getProgressReward();

        vo.setWeekIncome(weekIncome);
        vo.setWeekFine(weekFine);
        vo.setPersevereReward(persevereReward);
        vo.setProgressReward(progressReward);

        Integer netIncome = weekIncome + persevereReward + progressReward - weekFine;
        vo.setNetIncome(netIncome);
        return vo;


    }

    @Override
    public RuleVO toRule() {
        RuleVO vo = new RuleVO();
        vo.setDayRewardSignIn(businessConfig.get(BusinessConfig.DAY_REWARD_SIGN_IN));
        vo.setDayRewardDietMeal(businessConfig.get(BusinessConfig.DAY_REWARD_DIET_MEAL));
        vo.setDayRewardSports(businessConfig.get(BusinessConfig.DAY_REWARD_SPORTS));

        vo.setDayFineSignIn(businessConfig.get(BusinessConfig.DAY_FINE_SIGN_IN));
        vo.setDayFineDietMeal(businessConfig.get(BusinessConfig.DAY_FINE_DIET_MEAL));
        vo.setDayFineSports(businessConfig.get(BusinessConfig.DAY_FINE_SPORTS));

        vo.setWeekRewardPersevere(businessConfig.get(BusinessConfig.WEEK_REWARD_PERSEVERE));
        vo.setWeekRewardProgress(businessConfig.get(BusinessConfig.WEEK_REWARD_PROGRESS));

        vo.setFixedDayDietMeal(businessConfig.get(BusinessConfig.FIXED_DAY_DIET_MEAL));
        vo.setFixedDaySports(businessConfig.get(BusinessConfig.FIXED_DAY_SPORTS));
        return vo;
    }

    @Transactional
    @Override
    public void daySettlement(String userCode) {
        Date yesterday = MyDateUtils.getPastDate(new Date(), 1);
        String taskDate = MyDateUtils.dateToDateStr(yesterday, MyDateUtils.NORMAL_DATE_FORMAT);

        // 参数准备
        String yearWeek = MyDateUtils.getYearWeek(yesterday);
        int income = 0;
        int fine;


        Task taskDetail = taskMapper.getByTaskDateAndUserCode(taskDate, userCode);
        if (taskDetail == null) {
            Integer dietMealFlag = 0;
            Integer sportsFlag = 0;
            Task task = new Task();
            task.setTaskCode(KeyGenerator.getTaskCode());
            task.setTaskDate(taskDate);
            task.setYearWeek(yearWeek);
            task.setUserCode(userCode);
            task.setDietMealFlag(dietMealFlag);
            task.setSportsFlag(sportsFlag);

            int weekDayNum = MyDateUtils.getWeekDayNum(MyDateUtils.dateStrToDate(taskDate, MyDateUtils.NORMAL_DATE_FORMAT));
            boolean isDietMealDay = checkIsFixedDay(weekDayNum, BusinessConfig.FIXED_DAY_DIET_MEAL);
            boolean isSportsDay = checkIsFixedDay(weekDayNum, BusinessConfig.FIXED_DAY_SPORTS);

            task.setIncome(income);
            Integer dayFineSignIn = Integer.valueOf(businessConfig.get(BusinessConfig.DAY_FINE_SIGN_IN));
            fine = getFine(dietMealFlag, sportsFlag, isDietMealDay, isSportsDay) + dayFineSignIn;
            task.setFine(fine);
            task.setGrade(getGrade(0, fine));
            task.setTaskStatus(TaskStatusEnum.OFF_NORMAL.getCode());

            taskMapper.save(task);
        } else {
            income = taskDetail.getIncome();
            fine = taskDetail.getFine();
        }

        List<Bill> bills = getBills(userCode, yearWeek, income, fine);

        int money = income - fine;
        transactionalService.settlementSave(null, bills, userCode, money);

    }

    @Override
    public void weekSettlement(String userCode) {
        Date yesterday = MyDateUtils.getPastDate(new Date(), 1);
        String currentWeek = MyDateUtils.getYearWeek(yesterday);
        String lastWeek = MyDateUtils.getYearWeek(MyDateUtils.getPastDate(yesterday, 7));

        WeekIncomeVO vo = taskMapper.getWeekIncome(userCode, currentWeek);

        if (vo == null) {
            return;
        }

        List<Reward> rewards = new ArrayList<>(2);

        int persevereReward = getPersevereReward(userCode, currentWeek, vo, rewards);

        int progressReward = getProgressReward(userCode, currentWeek, lastWeek, rewards);

        int income = persevereReward + progressReward;

        List<Bill> bills = getBills(userCode, currentWeek, income, 0);

        transactionalService.settlementSave(rewards, bills, userCode, income);

    }

    private int getPersevereReward(String userCode, String currentWeek, WeekIncomeVO vo, List<Reward> rewards) {


        if (vo.getWeekFine() == null || vo.getWeekFine() == 0) {
            Reward reward = new Reward();
            reward.setYearWeek(currentWeek);
            reward.setUserCode(userCode);
            reward.setRewardType(RewardTypeEnum.PERSEVERE_REWARD.getCode());
            int money = Integer.valueOf(businessConfig.get(BusinessConfig.WEEK_REWARD_PERSEVERE));
            reward.setMoney(money);
            rewards.add(reward);
            return money;
        } else {
            System.out.println("全勤奖未达成");
        }
        return 0;

    }

    private int getProgressReward(String userCode, String currentWeek, String lastWeek, List<Reward> rewards) {
        String target = businessConfig.get(BusinessConfig.TARGET_PROGRESS);
        String currentWeekFinalWeight = taskMapper.getWeekFinalWeight(userCode, currentWeek);
        String lastWeekFinalWeight = taskMapper.getWeekFinalWeight(userCode, lastWeek);

        if (StringUtils.isNoneBlank(target, currentWeekFinalWeight, lastWeekFinalWeight)) {
            BigDecimal current = new BigDecimal(currentWeekFinalWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal last = new BigDecimal(lastWeekFinalWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal compareWeight = last.subtract(current);
            System.out.println("currentWeekFinalWeight:" + currentWeekFinalWeight);
            System.out.println("lastWeekFinalWeight:" + lastWeekFinalWeight);
            boolean progressFlag = compareWeight.compareTo(new BigDecimal(target)) > -1;
            if (progressFlag) {
                Reward reward = new Reward();
                reward.setYearWeek(currentWeek);
                reward.setUserCode(userCode);
                reward.setRewardType(RewardTypeEnum.PROGRESS_REWARD.getCode());
                int money = Integer.valueOf(businessConfig.get(BusinessConfig.WEEK_REWARD_PROGRESS));
                reward.setMoney(money);
                rewards.add(reward);
                return money;
            } else {
                System.out.println("进步奖未达成");
            }
        }
        return 0;
    }

    private List<Bill> getBills(String userCode,
                          String currentWeek,
                          int income,
                          int fine) {

        List<Bill> bills = new ArrayList<>(2);
        if (income > 0) {
            Bill bill = new Bill();
            bill.setUserCode(userCode);
            bill.setYearWeek(currentWeek);
            bill.setBillType(BillTypeEnum.INCOME.getCode());
            bill.setMoney(income);
            bills.add(bill);
        }

        if (fine > 0) {
            Bill bill = new Bill();
            bill.setUserCode(userCode);
            bill.setYearWeek(currentWeek);
            bill.setBillType(BillTypeEnum.FINE.getCode());
            bill.setMoney(fine);
            bills.add(bill);
        }
        return bills;

    }

    @Override
    public void sendSignInRemindMail(User user) {
        if (StringUtils.isBlank(user.getMailAccount())) {
            return;
        }

        String taskDate = MyDateUtils.dateToDateStr(new Date(), MyDateUtils.NORMAL_DATE_FORMAT);
        Task taskDetail = taskMapper.getByTaskDateAndUserCode(taskDate, user.getId().toString());

        if (taskDetail != null) {
            return;
        }

        String content = businessConfig.get(BusinessConfig.MAIL_TEMPLATE_SIGN_IN);
        content = content.replace("{0}", user.getNickName());

        System.out.println(content);
        try {
            MailUtils.sendMail(user.getMailAccount(), content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendFineRemindMail(User user) {
        if (StringUtils.isBlank(user.getMailAccount())) {
            return;
        }

        String taskDate = MyDateUtils.dateToDateStr(new Date(), MyDateUtils.NORMAL_DATE_FORMAT);
        Task taskDetail = taskMapper.getByTaskDateAndUserCode(taskDate, user.getId().toString());

        if (taskDetail == null) {
            return;
        }

        Integer dietMealFlag = taskDetail.getDietMealFlag();
        Integer sportsFlag = taskDetail.getSportsFlag();
        int weekDayNo = MyDateUtils.getWeekDayNum(MyDateUtils.dateStrToDate(taskDate, MyDateUtils.NORMAL_DATE_FORMAT));
        boolean isDietMealDay = checkIsFixedDay(weekDayNo, BusinessConfig.FIXED_DAY_DIET_MEAL);
        boolean isSportsDay = checkIsFixedDay(weekDayNo, BusinessConfig.FIXED_DAY_SPORTS);

        boolean dietMealFine = dietMealFlag == 0 && isDietMealDay;
        boolean sportsFine = sportsFlag == 0 && isSportsDay;

        if (!dietMealFine && !sportsFine) {
            return;
        }

        String subContent = "";
        if (dietMealFine) {
            subContent += "【代餐】";
        }

        if (sportsFine) {
            subContent += "【运动】";
        }
        String content = businessConfig.get(BusinessConfig.MAIL_TEMPLATE_FINE);

        content = content.replace("{0}", user.getNickName()).replace("{1}", subContent);

        try {
            MailUtils.sendMail(user.getMailAccount(), content);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
