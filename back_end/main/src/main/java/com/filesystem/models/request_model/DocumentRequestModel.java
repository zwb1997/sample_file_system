package com.filesystem.models.request_model;

public class DocumentRequestModel extends AbstractRequestModel {

    private String fileName;

    private String villageKey;

    private String fileUUID;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVillageKey() {
        return villageKey;
    }

    public void setVillageKey(String villageKey) {
        this.villageKey = villageKey;
    }

    public String getFileUUID() {
        return fileUUID;
    }

    public void setFileUUID(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    @Override
    public String toString() {
        return "DocumentRequestModel [fileName=" + fileName + ", villageKey=" + villageKey + ", fileUUID=" + fileUUID
                + "]";
    }

}
