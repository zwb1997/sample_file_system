package com.filesystem.models.execmodels;

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
