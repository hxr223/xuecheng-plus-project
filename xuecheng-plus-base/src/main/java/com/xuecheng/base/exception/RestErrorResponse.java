package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @ClassName RestErrorResponse
 * @Description 和前端约定返回的异常信息模型
 * @Author huangXuRui
 * @Date 2025/1/25 11:29
 */


public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
