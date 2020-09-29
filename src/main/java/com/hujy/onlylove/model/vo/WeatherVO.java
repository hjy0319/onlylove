package com.hujy.onlylove.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-25 17:42
 */
@Data
public class WeatherVO implements Serializable {

    private String position;

    private String temperature;

    private String weatherText;

    private String iconName;
}
