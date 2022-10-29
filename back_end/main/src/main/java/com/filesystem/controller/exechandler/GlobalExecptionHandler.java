package com.filesystem.controller.exechandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.filesystem.consts.enums.ServiceStatusEnum;
import com.filesystem.execeptions.RequiredParameterMissingException;
import com.filesystem.models.EmptyObject;
import com.filesystem.models.ResponseModel;
import com.filesystem.utils.ResponseBuilder;

/**
 * 
 */
@ControllerAdvice("com.filesystem.controller")
public class GlobalExecptionHandler {

    private static final Logger LOG = LogManager.getLogger(GlobalExecptionHandler.class);

    @ExceptionHandler({ RequiredParameterMissingException.class, Exception.class })
    @ResponseBody
    public ResponseModel<EmptyObject> handlerException(Exception ex) {

        LOG.error("request error! Message -> [{}]", ex.getMessage());
        return ResponseBuilder.build(new EmptyObject(), ServiceStatusEnum.FAILED, ex.getMessage());
    }
}
