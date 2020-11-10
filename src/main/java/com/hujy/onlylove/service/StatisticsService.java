package com.hujy.onlylove.service;

import com.hujy.onlylove.model.vo.TrendVO;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-11-10 13:57
 */
public interface StatisticsService {

    TrendVO trend(String userCode, String startDate, String endDate);
}
