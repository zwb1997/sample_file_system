package com.filesystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentTable {

    private String uuid;
    private String fileName;
    private String fileType;
    private String filePath;
    private String createTime;
    private String createCustomer;
    private int stateCode;
    private String updatedTime;
    private String deleteTime;
    private long fileSize;
    private String villageType;
    private String villageName;

    public String getUuid() {
        return uuid;
    }

    public DocumentTable setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DocumentTable setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DocumentTable setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public DocumentTable setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public DocumentTable setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateCustomer() {
        return createCustomer;
    }

    public DocumentTable setCreateCustomer(String createCustomer) {
        this.createCustomer = createCustomer;
        return this;
    }

    public int getStateCode() {
        return stateCode;
    }

    public DocumentTable setStateCode(int stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public DocumentTable setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public DocumentTable setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public long getFileSize() {
        return fileSize;
    }

    public DocumentTable setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getVillageType() {
        return villageType;
    }

    public DocumentTable setVillageType(String villageType) {
        this.villageType = villageType;
        return this;
    }

    public String getVillageName() {
        return villageName;
    }

    public DocumentTable setVillageName(String villageName) {
        this.villageName = villageName;
        return this;
    }

    @Override
    public String toString() {
        return "DocumentTable [uuid=" + uuid + ", fileName=" + fileName + ", fileType=" + fileType + ", filePath="
                + filePath + ", createTime=" + createTime + ", createCustomer=" + createCustomer + ", stateCode="
                + stateCode + ", updatedTime=" + updatedTime + ", deleteTime=" + deleteTime + ", fileSize=" + fileSize
                + ", villageType=" + villageType + ", villageName=" + villageName + "]";
    }

}
