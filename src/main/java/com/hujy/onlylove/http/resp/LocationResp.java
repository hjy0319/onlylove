package com.hujy.onlylove.http.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 14:46
 */
@Data
public class LocationResp implements Serializable {
    private String code;
    private List<Location> location;
}
