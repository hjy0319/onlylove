package com.hujy.onlylove.http.api;

import com.hujy.onlylove.http.resp.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-25 18:31
 */
@Component
public class LocationWeatherApi {

    /**
     * 和风天气
     * https://console.heweather.com/#/console
     */
    private static final String KEY = "f45c67641e8d4b17a2c63504a6b71e2e";

    private static final String LOCATION_URL = "https://geoapi.heweather.net/v2/city/lookup";

    private static final String WEATHER_URL = "https://devapi.heweather.net/v7/weather/now";

    private static String getParam(String lon, String lat) {
        return "?gzip=n&location=" + lon + "," + lat + "&key=" + KEY;
    }


    @Resource
    private HttpApi httpApi;

    public LocationWeatherResp getLocationWeather(String lon, String lat) {

        LocationWeatherResp resp = new LocationWeatherResp();
        String locationUrl = LOCATION_URL + getParam(lon, lat);
        LocationResp locationResp = httpApi.getForEntity(locationUrl, LocationResp.class);

        if (locationResp != null && "200".equals(locationResp.getCode())) {
            List<Location> locations = locationResp.getLocation();
            if (!CollectionUtils.isEmpty(locations)) {
                Location location = locations.get(0);
                StringBuilder sb = new StringBuilder();
                sb.append(location.getAdm1());
                if (!StringUtils.equals(location.getAdm1(), location.getAdm2())) {
                    sb.append(" | ").append(location.getAdm2());
                }

                if (!StringUtils.equals(location.getAdm1(), location.getName())) {
                    sb.append(" | ").append(location.getName());
                }

                resp.setLocation(sb.toString());
            }

        }

        String weatherUrl = WEATHER_URL + getParam(lon, lat);
        WeatherResp weatherResp = httpApi.getForEntity(weatherUrl, WeatherResp.class);
        if (weatherResp != null && "200".equals(weatherResp.getCode())) {
            Weather now = weatherResp.getNow();
            resp.setTemp(now.getTemp() + "°");
            resp.setText(now.getText());
            resp.setIcon(now.getIcon());

        }


        return resp;
    }


}
