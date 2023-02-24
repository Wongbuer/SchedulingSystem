package com.wong.ffwb.scheduling_system.exception.handler;

import com.wong.ffwb.scheduling_system.common.BaseResponse;
import com.wong.ffwb.scheduling_system.common.ErrorCode;
import com.wong.ffwb.scheduling_system.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class DuplicateEntryExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public BaseResponse<?> HandleDuplicateEntryException(SQLIntegrityConstraintViolationException e) {
        return ResultUtils.error(ErrorCode.OPERATION_ERROR, "已存在该数据");
    }
}
