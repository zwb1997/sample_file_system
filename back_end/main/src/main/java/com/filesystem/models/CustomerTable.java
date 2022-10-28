package com.filesystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTable {

    private long id;

    private String customerName;

    private String customerPass;

    private String createTime;

    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPass() {
        return customerPass;
    }

    public void setCustomerPass(String customerPass) {
        this.customerPass = customerPass;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CustomerTable [id=" + id + ", customerName=" + customerName + ", customerPass=" + customerPass
                + ", createTime=" + createTime + ", state=" + state + "]";
    }

}
