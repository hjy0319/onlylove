package com.hujy.onlylove.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.entity.Task;
import com.hujy.onlylove.enums.BillTypeEnum;
import com.hujy.onlylove.mapper.BillMapper;
import com.hujy.onlylove.model.param.BillPagingParam;
import com.hujy.onlylove.model.param.CashParam;
import com.hujy.onlylove.model.vo.TotalIncomeVO;
import com.hujy.onlylove.service.IncomeService;
import com.hujy.onlylove.service.TransactionalService;
import com.hujy.onlylove.util.MyDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-17 16:07
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Resource
    private BillMapper billMapper;

    @Resource
    private TransactionalService transactionalService;

    @Override
    public void cash(CashParam param) {
        String userCode = param.getUserCode();
        Integer money = param.getMoney();
        if (StringUtils.isBlank(userCode) || money == null || money <= 0) {
            throw new RuntimeException("参数错误");
        }

        Bill bill = new Bill();
        bill.setUserCode(userCode);
        String yearWeek = MyDateUtils.getYearWeek(new Date());
        bill.setYearWeek(yearWeek);
        bill.setMoney(money);
        bill.setBillType(BillTypeEnum.CASH.getCode());
        // 扣款
        money = - money;
        transactionalService.cash(Collections.singletonList(bill), userCode, money);

    }

    @Override
    public TotalIncomeVO toIncome(String userCode) {
        if (StringUtils.isBlank(userCode)) {
            throw new RuntimeException("参数错误");
        }

        TotalIncomeVO totalIncome = billMapper.getTotalIncome(userCode);
        totalIncome = Optional.ofNullable(totalIncome).orElse(new TotalIncomeVO());
        totalIncome.setTotalIncome(Optional.ofNullable(totalIncome.getTotalIncome()).orElse(0));
        totalIncome.setTotalFine(Optional.ofNullable(totalIncome.getTotalFine()).orElse(0));
        totalIncome.setTotalUsedMoney(Optional.ofNullable(totalIncome.getTotalUsedMoney()).orElse(0));
        if (totalIncome.getRestMoney() == null || totalIncome.getRestMoney() < 0) {
            totalIncome.setRestMoney(0);
        }

        return totalIncome;
    }

    @Override
    public Page<Bill> billPaging(BillPagingParam param) {
        if (StringUtils.isNotBlank(param.getBillDate())) {
            Date beginDate = MyDateUtils.dateStrToDate(param.getBillDate(), MyDateUtils.NORMAL_DATE_FORMAT);
            Date endDate = MyDateUtils.getPastDate(beginDate, -1);
            param.setBeginDate(beginDate);
            param.setEndDate(endDate);
        }
        Page pageCondition = new Page(param.getPageNum(), param.getPageSize(), true);
        Page<Bill> pageResult = billMapper.billPaging(pageCondition, param);
        if (pageResult != null) {
            List<Bill> records = pageResult.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                for (Bill record : records) {
                    record.setCreateTimeStr(MyDateUtils.dateToDateStr(record.getCreateTime(), MyDateUtils.NORMAL_DATE_TIME_FORMAT));
                }
            }
        }

        return pageResult;
    }

    public static void main(String[] args) {
        String date = "2020-09-15";
        Date date1 = MyDateUtils.dateStrToDate(date, MyDateUtils.NORMAL_DATE_FORMAT);
        Date date2 = MyDateUtils.getPastDate(date1, -1);
        System.out.println(date1);
        System.out.println(date2);
    }
}
