package com.wong.ffwb.scheduling_system.exception.handler;

import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.ErrorCode;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import com.wong.ffwb.scheduling_system.exception.NotSuchElementException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotSuchElementExceptionHandler {
    @ExceptionHandler({NotSuchElementException.class})
    public BaseResponse<?> handleNotSuchElementException(NotSuchElementException e) {
        return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR, e.getMessage());
    }
}
