package com.hujy.onlylove.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.model.param.BillPagingParam;
import com.hujy.onlylove.model.param.CashParam;
import com.hujy.onlylove.model.vo.TotalIncomeVO;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 17:50
 */
public interface IncomeService {

    void cash(CashParam param);

    TotalIncomeVO toIncome(String userCode);

    Page<Bill> billPaging(BillPagingParam param);

}
