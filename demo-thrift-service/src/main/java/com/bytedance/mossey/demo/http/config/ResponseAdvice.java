package com.bytedance.mossey.demo.http.config;

import com.bytedance.mossey.demo.common.constants.StatusEnum;
import com.bytedance.mossey.demo.common.exception.BizException;
import com.bytedance.mossey.demo.common.json.JacksonUtil;
import com.bytedance.mossey.demo.http.response.ArgNotValidError;
import com.bytedance.mossey.demo.http.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 统一处理controllers返回
 * @Author: xieao.mossey@bytedance.com
 * @Date: 2023年06月21日
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.bytedance.mossey.demo.http.controllers"})
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        return !returnType.getGenericParameterType().equals(CommonResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
        ServerHttpResponse serverHttpResponse) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            return JacksonUtil.writeValueAsString(CommonResponse.success(data));
        }
        // 将原本的数据包装在ResultVO里
        return CommonResponse.success(data);
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<Object> bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage());
        return CommonResponse.fail(e.getCode(), e.getMessage(), e.getData());
    }

    @ResponseBody
    @ExceptionHandler(value = ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> serverErrorExceptionHandler(HttpServletRequest req, ServerErrorException e) {
        log.error("发生500异常！原因是：{}", e.getMessage());
        return CommonResponse.fail(StatusEnum.SERVICE_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<List<ArgNotValidError>> argNotValidHandler(HttpServletRequest req,
        MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<ArgNotValidError> errors = new ArrayList<>();

        allErrors.forEach(objectError -> {
            ArgNotValidError error = new ArgNotValidError();
            FieldError fieldError = (FieldError)objectError;
            error.setField(fieldError.getField());
            error.setObjectName(fieldError.getObjectName());
            error.setMessage(fieldError.getDefaultMessage());
            errors.add(error);
        });
        return CommonResponse.fail(StatusEnum.PARAM_INVALID, errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<String> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("[ExceptionHandler] Exception: {}", e.getCause(), e);
        return CommonResponse.fail(StatusEnum.SERVICE_ERROR.getCode(), e.getMessage());
    }
}
