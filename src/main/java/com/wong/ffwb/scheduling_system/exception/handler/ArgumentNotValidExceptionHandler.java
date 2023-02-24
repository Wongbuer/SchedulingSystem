package com.wong.ffwb.scheduling_system.exception.handler;

import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.ErrorCode;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ArgumentNotValidExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public BaseResponse<?> handleArgumentNotValidException(Exception e) {
        Set<String> errorMessages = new HashSet<>();
        if (e instanceof ConstraintViolationException) {
            errorMessages.add("页码与请求数必须是正数或零");
        } else {
            ((MethodArgumentNotValidException) e).getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));
        }
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, errorMessages);
    }
}
