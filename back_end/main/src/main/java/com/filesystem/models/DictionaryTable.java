package com.filesystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryTable {

    private String uuid;
    private String codeName;
    private String codeValue;
    private int codeState;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public int getCodeState() {
        return codeState;
    }

    public void setCodeState(int codeState) {
        this.codeState = codeState;
    }

    @Override
    public String toString() {
        return "DictionaryTable [uuid=" + uuid + ", codeName=" + codeName + ", codeValue=" + codeValue + ", codeState="
                + codeState + "]";
    }

}
