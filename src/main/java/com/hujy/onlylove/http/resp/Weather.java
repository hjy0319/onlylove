package com.hujy.onlylove.http.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-25 18:16
 */
@Data
public class Weather implements Serializable {
    private String temp;
    private String text;
    private String icon;
}
