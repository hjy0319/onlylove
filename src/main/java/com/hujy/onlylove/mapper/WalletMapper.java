package com.hujy.onlylove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hujy.onlylove.entity.Reward;
import com.hujy.onlylove.entity.Wallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 15:17
 */
public interface WalletMapper extends BaseMapper<Wallet> {

    Integer updateMoney(@Param("userCode") String userCode, @Param("money") Integer money);

}
