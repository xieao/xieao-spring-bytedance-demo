package com.bytedance.mossey.demo.common.exception;

/**
 * JSONSyntaxRuntimeException
 *
 * @author xieao.mossey
 * @version 2024/03/18 16:29
 **/
public class JSONSyntaxRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public JSONSyntaxRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
