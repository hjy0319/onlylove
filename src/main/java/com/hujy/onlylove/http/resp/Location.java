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
public class Location implements Serializable {
    private String country;

    private String adm1;

    private String adm2;

    private String name;

    private String lon;

    private String lat;
}
