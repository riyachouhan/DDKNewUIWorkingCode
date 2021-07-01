package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class sfioModel implements Serializable
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

    public static class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("trx_number")
        @Expose
        private String trxNumber;

        @SerializedName("remaining_days")
        @Expose
        private String remaining_days;

        @SerializedName("txnid")
        @Expose
        private String txnid;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("trx_link")
        @Expose
        private String trxLink;
        @SerializedName("subscription_date")
        @Expose
        private String subscriptionDate;
        @SerializedName("expiration_date")
        @Expose
        private String expirationDate;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;

        @SerializedName("upload_btn")
        @Expose
        private String upload_btn;

        @SerializedName("cancellation_btn_status")
        @Expose
        private String cancellation_btn_status;

        @SerializedName("cancellation_status")
        @Expose
        private String cancellation_status;

        @SerializedName("bank_status")
        @Expose
        private String bank_status;

        @SerializedName("reason_for_rejection")
        @Expose
        private String reason_for_rejection;

        @SerializedName("created_at")
        @Expose
        private String createdAt;
        private final static long serialVersionUID = -4091787012815513625L;

        public String getReason_for_rejection() {
            return reason_for_rejection;
        }

        public void setReason_for_rejection(String reason_for_rejection) {
            this.reason_for_rejection = reason_for_rejection;
        }

        public String getBank_status() {
            return bank_status;
        }

        public void setBank_status(String bank_status) {
            this.bank_status = bank_status;
        }

        public String getRemaining_days() {
            return remaining_days;
        }

        public String getCancellation_btn_status() {
            return cancellation_btn_status;
        }

        public void setCancellation_btn_status(String cancellation_btn_status) {
            this.cancellation_btn_status = cancellation_btn_status;
        }

        public String getUpload_btn() {
            return upload_btn;
        }

        public void setUpload_btn(String upload_btn) {
            this.upload_btn = upload_btn;
        }

        public String getCancellation_status() {
            return cancellation_status;
        }

        public void setCancellation_status(String cancellation_status) {
            this.cancellation_status = cancellation_status;
        }

        public void setRemaining_days(String remaining_days) {
            this.remaining_days = remaining_days;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getTrxNumber() {
            return trxNumber;
        }

        public void setTrxNumber(String trxNumber) {
            this.trxNumber = trxNumber;
        }

        public String getTxnid() {
            return txnid;
        }

        public void setTxnid(String txnid) {
            this.txnid = txnid;
        }

        public String getTrxLink() {
            return trxLink;
        }

        public void setTrxLink(String trxLink) {
            this.trxLink = trxLink;
        }

        public String getSubscriptionDate() {
            return subscriptionDate;
        }

        public void setSubscriptionDate(String subscriptionDate) {
            this.subscriptionDate = subscriptionDate;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
