package com.ddkcommunity.fragment.mapmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class finnelModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -4815242821389117027L;

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
        @SerializedName("package_status")
        @Expose
        private String packageStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("activate_date")
        @Expose
        private Object activateDate;
        @SerializedName("approve_date")
        @Expose
        private Object approveDate;
        @SerializedName("pack_amt")
        @Expose
        private Integer packAmt;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("payment_mode")
        @Expose
        private String paymentMode;
        private final static long serialVersionUID = 6384951926182320467L;

        public String getPackageStatus() {
            return packageStatus;
        }

        public void setPackageStatus(String packageStatus) {
            this.packageStatus = packageStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getActivateDate() {
            return activateDate;
        }

        public void setActivateDate(Object activateDate) {
            this.activateDate = activateDate;
        }

        public Object getApproveDate() {
            return approveDate;
        }

        public void setApproveDate(Object approveDate) {
            this.approveDate = approveDate;
        }

        public Integer getPackAmt() {
            return packAmt;
        }

        public void setPackAmt(Integer packAmt) {
            this.packAmt = packAmt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

    }
}
