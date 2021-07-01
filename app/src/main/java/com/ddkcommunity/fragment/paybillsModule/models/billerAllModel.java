package com.ddkcommunity.fragment.paybillsModule.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class billerAllModel implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -4474516099102627109L;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum implements Serializable
    {

        @SerializedName("biller_id")
        @Expose
        private Integer billerId;
        @SerializedName("biller_name")
        @Expose
        private String billerName;
        @SerializedName("biller_value")
        @Expose
        private String billerValue;
        @SerializedName("biller_service_fees")
        @Expose
        private Integer billerServiceFees;
        @SerializedName("biller_category")
        @Expose
        private String billerCategory;
        @SerializedName("biller_resource")
        @Expose
        private String billerResource;
        @SerializedName("biller_account_format")
        @Expose
        private Object billerAccountFormat;
        @SerializedName("biller_image")
        @Expose
        private String billerImage;
        private final static long serialVersionUID = -8375689909066446980L;

        public Integer getBillerId() {
            return billerId;
        }

        public void setBillerId(Integer billerId) {
            this.billerId = billerId;
        }

        public String getBillerName() {
            return billerName;
        }

        public void setBillerName(String billerName) {
            this.billerName = billerName;
        }

        public String getBillerValue() {
            return billerValue;
        }

        public void setBillerValue(String billerValue) {
            this.billerValue = billerValue;
        }

        public Integer getBillerServiceFees() {
            return billerServiceFees;
        }

        public void setBillerServiceFees(Integer billerServiceFees) {
            this.billerServiceFees = billerServiceFees;
        }

        public String getBillerCategory() {
            return billerCategory;
        }

        public void setBillerCategory(String billerCategory) {
            this.billerCategory = billerCategory;
        }

        public String getBillerResource() {
            return billerResource;
        }

        public void setBillerResource(String billerResource) {
            this.billerResource = billerResource;
        }

        public Object getBillerAccountFormat() {
            return billerAccountFormat;
        }

        public void setBillerAccountFormat(Object billerAccountFormat) {
            this.billerAccountFormat = billerAccountFormat;
        }

        public String getBillerImage() {
            return billerImage;
        }

        public void setBillerImage(String billerImage) {
            this.billerImage = billerImage;
        }

    }
}
