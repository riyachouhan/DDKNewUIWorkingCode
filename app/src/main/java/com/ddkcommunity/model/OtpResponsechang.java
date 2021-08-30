package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponsechang {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("msg")
    @Expose
    public String msg;

}