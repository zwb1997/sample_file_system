package com.filesystem.models.request_model;

public class DictionaryRequestModel extends AbstractRequestModel {

    public DictionaryRequestModel() {
    }

    private String codeName;

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public String toString() {
        super.toString();
        return "DictionaryRequestModel [codeName=" + codeName + "]";
    }

}
