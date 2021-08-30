package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class owneeshipstausModel implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private int status;

    private final static long serialVersionUID = 5437581285438779856L;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}