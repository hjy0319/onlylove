package com.hujy.onlylove.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author hujy
 * @version 1.0
 * @date 2019-10-29 14:31
 */
@Data
public class RespVO<T> implements Serializable {

    public final static String SUCCESS_CODE = "200";

    public final static String FAIL_CODE = "500";

    public final static String SUCCESS_STATUS = "success";

    public final static String SUCCESS_MESSAGE = "成功";

    public final static String FAIL_STATUS = "fail";

    private static final long serialVersionUID = 8304183575658291523L;

    private String code;
    private String status;
    private String message;
    // 业务数据体
    private T data;

    public RespVO() {
        this.code = SUCCESS_CODE;
        this.status = SUCCESS_STATUS;
        this.message = SUCCESS_MESSAGE;
    }

    public RespVO(T data) {
        this.code = SUCCESS_CODE;
        this.status = SUCCESS_STATUS;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
    }

    public RespVO(String code, String message) {
        this.code = code;
        this.status = FAIL_STATUS;
        this.message = message;
    }

    public RespVO(String code, String status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static RespVO ok(Object data) {
        return new RespVO(data);
    }

    public static RespVO ok() {
        return new RespVO();
    }
    public static RespVO faliure(String msg){
        return new RespVO(FAIL_CODE,FAIL_STATUS,msg,null);
    }
    public static RespVO faliure(){
        return faliure(null);
    }
    public Boolean isSuccess() {
        return SUCCESS_STATUS.equals(status);
    }


}
