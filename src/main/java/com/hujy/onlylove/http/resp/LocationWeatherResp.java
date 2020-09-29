package com.hujy.onlylove.http.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 14:46
 */
@Data
public class LocationWeatherResp implements Serializable {
    private String location;
    private String temp;
    private String text;
    private String icon;
}
