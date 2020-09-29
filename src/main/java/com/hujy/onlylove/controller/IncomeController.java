package com.hujy.onlylove.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.model.param.BillPagingParam;
import com.hujy.onlylove.model.param.CashParam;
import com.hujy.onlylove.model.vo.RespVO;
import com.hujy.onlylove.model.vo.TotalIncomeVO;
import com.hujy.onlylove.service.IncomeService;
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
@RequestMapping("income")
public class IncomeController {

    @Resource
    private IncomeService incomeService;

    @PostMapping("/cash")
    public RespVO<String> cash(@RequestBody CashParam param) {
        incomeService.cash(param);
        return new RespVO<>("ok");
    }

    @GetMapping("/toIncome")
    public RespVO<TotalIncomeVO> toIncome(@RequestParam String userCode) {
        TotalIncomeVO vo = incomeService.toIncome(userCode);
        return new RespVO<>(vo);
    }

    @PostMapping("/billPaging")
    public RespVO<Page<Bill>> billPaging(@RequestBody BillPagingParam param) {
        Page<Bill> billPage = incomeService.billPaging(param);
        return new RespVO<>(billPage);
    }



}
