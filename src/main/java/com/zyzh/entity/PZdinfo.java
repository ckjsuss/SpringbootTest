package com.zyzh.entity;

import java.io.Serializable;
import java.util.Date;

public class PZdinfo implements Serializable {
    private String zdCode;

    private String zdState;

    private String zdTitle;

    private String zdType;

    private String zdAddress;

    private Date createTime;

    private Date editTime;

    private static final long serialVersionUID = 1L;

    public String getZdCode() {
        return zdCode;
    }

    public void setZdCode(String zdCode) {
        this.zdCode = zdCode == null ? null : zdCode.trim();
    }

    public String getZdState() {
        return zdState;
    }

    public void setZdState(String zdState) {
        this.zdState = zdState == null ? null : zdState.trim();
    }

    public String getZdTitle() {
        return zdTitle;
    }

    public void setZdTitle(String zdTitle) {
        this.zdTitle = zdTitle == null ? null : zdTitle.trim();
    }

    public String getZdType() {
        return zdType;
    }

    public void setZdType(String zdType) {
        this.zdType = zdType == null ? null : zdType.trim();
    }

    public String getZdAddress() {
        return zdAddress;
    }

    public void setZdAddress(String zdAddress) {
        this.zdAddress = zdAddress == null ? null : zdAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}