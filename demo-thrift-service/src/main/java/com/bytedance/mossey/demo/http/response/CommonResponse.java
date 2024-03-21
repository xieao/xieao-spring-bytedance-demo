package com.bytedance.mossey.demo.http.response;

import com.bytedance.mossey.demo.common.constants.StatusEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResponse<T> implements Serializable {
    private static final long serialVersionUID = -1133637474601003587L;

    /**
     * 结果状态
     */
    private Integer code;
    private String message;
    private T data;
    private long timestamp;

    public CommonResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> resultData = new CommonResponse<>();
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMessage(StatusEnum.SUCCESS.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> CommonResponse<T> fail(Integer code, String message) {
        CommonResponse<T> resultData = new CommonResponse<>();
        resultData.setCode(code == null ? StatusEnum.SERVICE_ERROR.getCode() : code);
        resultData.setMessage(message);
        return resultData;
    }

    public static <T> CommonResponse<T> fail(Integer code, String message, T data) {
        CommonResponse<T> resultData = new CommonResponse<>();
        resultData.setCode(code == null ? StatusEnum.SERVICE_ERROR.getCode() : code);
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }

    public static <T> CommonResponse<T> fail(StatusEnum code, T data) {
        CommonResponse<T> resultData = new CommonResponse<>();
        resultData.setCode(code.getCode());
        resultData.setMessage(code.getMessage());
        resultData.setData(data);
        return resultData;
    }
}
