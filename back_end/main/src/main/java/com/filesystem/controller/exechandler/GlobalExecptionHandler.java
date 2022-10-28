package com.filesystem.controller.exechandler;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.filesystem.consts.enums.ServiceStatusEnum;
import com.filesystem.execeptions.RequiredParameterMissingException;
import com.filesystem.models.Demo;
import com.filesystem.models.ResponseModel;
import com.filesystem.utils.ResponseBuilder;

/**
 * 
 */
@ControllerAdvice("com.filesystem.controller")
public class GlobalExecptionHandler {

    @ExceptionHandler(RequiredParameterMissingException.class)
    @ResponseBody
    public ResponseModel<Object> handlerException(Exception ex) {

        return ResponseBuilder.build(new Demo(), ServiceStatusEnum.FAILED, ex.getMessage());
    }
}
