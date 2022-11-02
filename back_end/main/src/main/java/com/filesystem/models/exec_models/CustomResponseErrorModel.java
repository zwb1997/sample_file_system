package com.filesystem.models.exec_models;

public class CustomResponseErrorModel {

    private String customErrorMessage;

    public String getCustomErrorMessage() {
        return customErrorMessage;
    }

    public void setCustomErrorMessage(String customErrorMessage) {
        this.customErrorMessage = customErrorMessage;
    }

    @Override
    public String toString() {
        return "CustomResponseErrorModel [customErrorMessage=" + customErrorMessage + "]";
    }

}
