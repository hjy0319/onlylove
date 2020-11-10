package com.hujy.onlylove.controller;

import com.hujy.onlylove.model.vo.RespVO;
import com.hujy.onlylove.model.vo.TrendVO;
import com.hujy.onlylove.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-11-10 13:54
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @GetMapping("/trend")
    public RespVO<TrendVO> trend(@RequestParam String userCode, String startDate, String endDate) {
        TrendVO trend = statisticsService.trend(userCode, startDate, endDate);
        return new RespVO<>(trend);
    }
}
