package com.filesystem.models;

import java.io.File;

public class SavedFileModel {

    private String originalFileName;
    private String originalFileType;
    private String savedFileName;
    private String fileVillageType;

    private File cacheFile;

    public String getOriginalFileType() {
        return originalFileType;
    }

    public SavedFileModel setOriginalFileType(String originalFileType) {
        this.originalFileType = originalFileType;
        return this;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public SavedFileModel setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        return this;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public SavedFileModel setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
        return this;
    }

    public String getFileVillageType() {
        return fileVillageType;
    }

    public SavedFileModel setFileVillageType(String fileVillageType) {
        this.fileVillageType = fileVillageType;
        return this;
    }

    public File getCacheFile() {
        return cacheFile;
    }

    public SavedFileModel setCacheFile(File cacheFile) {
        this.cacheFile = cacheFile;
        return this;
    }

    @Override
    public String toString() {
        return "SavedFileModel [originalFileName=" + originalFileName + ", originalFileType=" + originalFileType
                + ", savedFileName=" + savedFileName + ", fileVillageType=" + fileVillageType + ", cacheFile="
                + cacheFile + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((originalFileName == null) ? 0 : originalFileName.hashCode());
        result = prime * result + ((fileVillageType == null) ? 0 : fileVillageType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SavedFileModel other = (SavedFileModel) obj;
        if (originalFileName == null) {
            if (other.originalFileName != null)
                return false;
        } else if (!originalFileName.equals(other.originalFileName))
            return false;
        if (fileVillageType == null) {
            if (other.fileVillageType != null)
                return false;
        } else if (!fileVillageType.equals(other.fileVillageType))
            return false;
        return true;
    }

}
