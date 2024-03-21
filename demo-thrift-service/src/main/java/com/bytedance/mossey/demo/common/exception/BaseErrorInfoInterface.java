package com.bytedance.mossey.demo.common.exception;

public interface BaseErrorInfoInterface {
    /**
     * 错误码
     */
    Integer getCode();

    /**
     * 错误描述
     */
    String getMessage();
}
