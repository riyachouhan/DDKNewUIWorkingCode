package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class sfioSubPackageModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -4474516099102627109L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sfio_id")
        @Expose
        private Integer sfioId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("trx_no")
        @Expose
        private String trxNo;

        @SerializedName("redeem_btn_status")
        @Expose
        private String redeem_btn_status;

        @SerializedName("date")
        @Expose
        private String date;

        @SerializedName("amount")
        @Expose
        private String amount;

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        private final static long serialVersionUID = -314881158284241600L;

        public String getRedeem_btn_status() {
            return redeem_btn_status;
        }

        public void setRedeem_btn_status(String redeem_btn_status) {
            this.redeem_btn_status = redeem_btn_status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getId() {
            return id;
        }

        public String getTrxNo() {
            return trxNo;
        }

        public void setTrxNo(String trxNo) {
            this.trxNo = trxNo;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSfioId() {
            return sfioId;
        }

        public void setSfioId(Integer sfioId) {
            this.sfioId = sfioId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
