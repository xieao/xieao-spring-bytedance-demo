package com.bytedance.mossey.demo.http.response;

import lombok.Data;

/**
 * @Description: ArgNotValidError
 * @Author: xieao.mossey@bytedance.com
 * @Date: 2023年06月21日
 */
@Data
public class ArgNotValidError {
    private String field;
    private String objectName;
    private String message;
}
