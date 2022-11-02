package com.filesystem.models.request_model;

public abstract class AbstractRequestModel {

    public AbstractRequestModel() {
    }

    private int totalCount = 0;
    private int pageNum = 1;
    private int pageSize = 10;
    private int totalPages = 0;

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "AbstractRequestModel [totalCount=" + totalCount + ", pageNum=" + pageNum + ", pageSize=" + pageSize
                + ", totalPages=" + totalPages + "]";
    }

}
