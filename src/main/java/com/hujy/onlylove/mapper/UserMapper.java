package com.hujy.onlylove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hujy.onlylove.entity.Bill;
import com.hujy.onlylove.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

    Integer save(@Param("user") User user);

    Integer getId(@Param("nickName") String nickName);

    List<User> getAll();

}
