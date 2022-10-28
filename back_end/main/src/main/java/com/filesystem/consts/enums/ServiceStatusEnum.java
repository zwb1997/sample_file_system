package com.filesystem.consts.enums;

public enum ServiceStatusEnum {

    SUCCESS("successful service", 1),
    FAILED("unsuccessful service", 0);

    private String serviceMessage;
    private int serviceCode;

    private ServiceStatusEnum() {
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public String getserviceMessage() {
        return serviceMessage;
    }

    private ServiceStatusEnum(String serviceMessage, int serviceCode) {
        this.serviceMessage = serviceMessage;
        this.serviceCode = serviceCode;
    }

}
