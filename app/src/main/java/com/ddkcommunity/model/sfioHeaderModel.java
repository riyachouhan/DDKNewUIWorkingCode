package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class sfioHeaderModel  implements Serializable
{

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = 8895702739878110052L;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable
    {

        @SerializedName("points")
        @Expose
        private String points;
        @SerializedName("bank_deposit")
        @Expose
        private List<BankDeposit> bankDeposit = null;
        private final static long serialVersionUID = -2791490000993558259L;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public List<BankDeposit> getBankDeposit() {
            return bankDeposit;
        }

        public void setBankDeposit(List<BankDeposit> bankDeposit) {
            this.bankDeposit = bankDeposit;
        }

    }

    public class BankDeposit implements Serializable
    {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("trx_number")
        @Expose
        private String trxNumber;
        @SerializedName("txnid")
        @Expose
        private Object txnid;
        @SerializedName("amount")
        @Expose
        private int amount;
        @SerializedName("trx_link")
        @Expose
        private String trxLink;
        @SerializedName("fee")
        @Expose
        private int fee;
        @SerializedName("total_amount")
        @Expose
        private int totalAmount;
        @SerializedName("subscription_date")
        @Expose
        private String subscriptionDate;
        @SerializedName("expiration_date")
        @Expose
        private String expirationDate;
        @SerializedName("remaining_days")
        @Expose
        private int remainingDays;
        @SerializedName("payment_mode")
        @Expose
        private String paymentMode;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("cancellation_status")
        @Expose
        private String cancellationStatus;
        @SerializedName("reason_for_rejection")
        @Expose
        private String reasonForRejection;
        @SerializedName("bank_status")
        @Expose
        private String bankStatus;
        @SerializedName("upload_btn")
        @Expose
        private int uploadBtn;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        private final static long serialVersionUID = 191657114003692371L;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTrxNumber() {
            return trxNumber;
        }

        public void setTrxNumber(String trxNumber) {
            this.trxNumber = trxNumber;
        }

        public Object getTxnid() {
            return txnid;
        }

        public void setTxnid(Object txnid) {
            this.txnid = txnid;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getTrxLink() {
            return trxLink;
        }

        public void setTrxLink(String trxLink) {
            this.trxLink = trxLink;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
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

        public int getRemainingDays() {
            return remainingDays;
        }

        public void setRemainingDays(int remainingDays) {
            this.remainingDays = remainingDays;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getCancellationStatus() {
            return cancellationStatus;
        }

        public void setCancellationStatus(String cancellationStatus) {
            this.cancellationStatus = cancellationStatus;
        }

        public String getReasonForRejection() {
            return reasonForRejection;
        }

        public void setReasonForRejection(String reasonForRejection) {
            this.reasonForRejection = reasonForRejection;
        }

        public String getBankStatus() {
            return bankStatus;
        }

        public void setBankStatus(String bankStatus) {
            this.bankStatus = bankStatus;
        }

        public int getUploadBtn() {
            return uploadBtn;
        }

        public void setUploadBtn(int uploadBtn) {
            this.uploadBtn = uploadBtn;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
