package com.hujy.onlylove.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-11-10 14:37
 */
@Data
public class TrendVO implements Serializable {

    private String title;

    private List<String> x;

    private List<String> y;
}
