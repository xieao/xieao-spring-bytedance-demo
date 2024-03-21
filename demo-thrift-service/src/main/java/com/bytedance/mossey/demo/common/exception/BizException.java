package com.bytedance.mossey.demo.common.exception;

import com.bytedance.mossey.demo.common.constants.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 业务异常
 * @Author: xieao.mossey@bytedance.com
 * @Date: 2023年06月21日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String message;

    protected Object data;

    public BizException() {
        super();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getMessage());
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMessage();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getMessage(), cause);
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMessage();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.message = errorMsg;
    }

    public BizException(String errorMsg, Object data) {
        super(errorMsg);
        this.message = errorMsg;
        this.data = data;
    }

    public BizException(StatusEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BizException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.code = errorCode;
        this.message = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg, Object data) {
        super(errorMsg);
        this.code = errorCode;
        this.message = errorMsg;
        this.data = data;
    }

    public BizException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.code = errorCode;
        this.message = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg, Object data, Throwable cause) {
        super(errorMsg, cause);
        this.code = errorCode;
        this.message = errorMsg;
        this.data = data;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
