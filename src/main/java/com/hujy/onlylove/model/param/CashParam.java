package com.hujy.onlylove.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-17 15:57
 */
@Data
public class CashParam implements Serializable {

    private String userCode;

    private Integer money;
}
