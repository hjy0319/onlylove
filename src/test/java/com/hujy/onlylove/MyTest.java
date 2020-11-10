package com.hujy.onlylove;

import com.hujy.onlylove.http.api.LocationWeatherApi;
import com.hujy.onlylove.http.resp.LocationWeatherResp;
import com.hujy.onlylove.model.vo.TrendVO;
import com.hujy.onlylove.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-26 09:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Resource
    private LocationWeatherApi locationWeatherApi;

    @Resource
    private StatisticsService statisticsService;

    @Test
    public void testApi() {
        LocationWeatherResp locationWeather = locationWeatherApi.getLocationWeather("116.4", "39.1");
    }

    @Test
    public void testStatis() {
        TrendVO trend = statisticsService.trend("9", "2020-10-01", "2020-10-31");
        System.out.println(trend);
    }
}
