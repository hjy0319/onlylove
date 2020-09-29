package com.hujy.onlylove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hujy.onlylove.entity.Config;
import org.apache.ibatis.annotations.Param;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-09 15:17
 */
public interface ConfigMapper extends BaseMapper<Config> {

    String get(@Param("configKey") String configKey);
}
