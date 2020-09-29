package com.hujy.onlylove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.model.param.BillPagingParam;
import com.hujy.onlylove.model.vo.TotalIncomeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 15:17
 */
public interface BillMapper extends BaseMapper<Bill> {

    Integer batchInsert(@Param("bills") List<Bill> bills);

    TotalIncomeVO getTotalIncome(@Param("userCode") String userCode);

    Page<Bill> billPaging(Page page, @Param("param") BillPagingParam param);

}
