package com.filesystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryTable {

    private long id;
    private String codeName;
    private String codeValue;
    private int codeState;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "DictionaryTable [id=" + id + ", codeName=" + codeName + ", codeValue=" + codeValue + ", codeState="
                + codeState + "]";
    }

}
