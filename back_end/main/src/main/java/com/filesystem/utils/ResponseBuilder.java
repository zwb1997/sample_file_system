package com.filesystem.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.filesystem.consts.enums.ServiceStatusEnum;
import com.filesystem.models.ResponseModel;

public class ResponseBuilder {

    private static final Logger LOG = LogManager.getLogger(ResponseBuilder.class);

    /**
     * build the model, decided
     * by {@link com.filesystem.consts.enums.ServiceStatusEnum}
     * 
     * @param <T>
     * @param data
     * @param statusEnum
     * @return
     */
    public static <T> ResponseModel<T> build(T data, ServiceStatusEnum statusEnum, String errorMessage) {

        ResponseModel<T> model = null;
        switch (statusEnum) {
            case SUCCESS:
                model = succeedBuild(data);
                break;
            case FAILED:
                model = failedBuild(data, errorMessage);
                break;
            default:
                failedBuild(data, errorMessage);
                break;
        }

        return model;
    }

    /**
     * build success service response model
     * 
     * @param <T>
     * @param data
     * @return
     */
    private static <T> ResponseModel<T> succeedBuild(T data) {

        ResponseModel<T> model = new ResponseModel<>();

        model.setData(data);
        model.setMessage(ServiceStatusEnum.SUCCESS.getserviceMessage());
        model.setCode(ServiceStatusEnum.SUCCESS.getServiceCode());
        model.setErrorMessage(StringUtils.EMPTY);
        return model;
    }

    /**
     * 
     * 
     * @param <T>
     * @param data
     * @param statusEnum
     * @return
     */

    /**
     * build failed service response model
     * 
     * @param <T>
     * @param data
     * @return
     */
    private static <T> ResponseModel<T> failedBuild(T data, String errorMessage) {

        ResponseModel<T> model = new ResponseModel<>();

        model.setData(data);
        model.setMessage(ServiceStatusEnum.FAILED.getserviceMessage());
        model.setCode(ServiceStatusEnum.FAILED.getServiceCode());
        model.setErrorMessage(errorMessage);
        return model;
    }

}
