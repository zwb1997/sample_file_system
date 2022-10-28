package com.filesystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkFilesTable {

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateCustomer() {
        return createCustomer;
    }

    public void setCreateCustomer(String createCustomer) {
        this.createCustomer = createCustomer;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "WorkFilesTable [uuid=" + uuid + ", fileName=" + fileName + ", fileType=" + fileType + ", filePath="
                + filePath + ", createTime=" + createTime + ", createCustomer=" + createCustomer + ", stateCode="
                + stateCode + ", updatedTime=" + updatedTime + ", deleteTime=" + deleteTime + ", fileSize=" + fileSize
                + "]";
    }

}
