package com.hujy.onlylove.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 15:38
 */
@Data
public class DecodeUserInfoParam implements Serializable {
    private String encryptedData;
    private String iv;
    private String code;
}
