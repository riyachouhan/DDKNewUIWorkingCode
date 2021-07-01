package com.ddkcommunity.fragment.mapmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class directReferralModel implements Serializable
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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("trx_id")
        @Expose
        private String trxId;
        @SerializedName("package_id")
        @Expose
        private Integer packageId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("user_referred")
        @Expose
        private Integer userReferred;
        @SerializedName("bonus_type")
        @Expose
        private String bonusType;
        @SerializedName("points")
        @Expose
        private String points;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("is_overflow")
        @Expose
        private Integer isOverflow;
        @SerializedName("overflow_expiration_date")
        @Expose
        private Object overflowExpirationDate;
        @SerializedName("bonus_status")
        @Expose
        private Integer bonusStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("renewal_no")
        @Expose
        private Integer renewalNo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("pack_amt")
        @Expose
        private Integer packAmt;
        private final static long serialVersionUID = -2823975415360526710L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTrxId() {
            return trxId;
        }

        public void setTrxId(String trxId) {
            this.trxId = trxId;
        }

        public Integer getPackageId() {
            return packageId;
        }

        public void setPackageId(Integer packageId) {
            this.packageId = packageId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getUserReferred() {
            return userReferred;
        }

        public void setUserReferred(Integer userReferred) {
            this.userReferred = userReferred;
        }

        public String getBonusType() {
            return bonusType;
        }

        public void setBonusType(String bonusType) {
            this.bonusType = bonusType;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getIsOverflow() {
            return isOverflow;
        }

        public void setIsOverflow(Integer isOverflow) {
            this.isOverflow = isOverflow;
        }

        public Object getOverflowExpirationDate() {
            return overflowExpirationDate;
        }

        public void setOverflowExpirationDate(Object overflowExpirationDate) {
            this.overflowExpirationDate = overflowExpirationDate;
        }

        public Integer getBonusStatus() {
            return bonusStatus;
        }

        public void setBonusStatus(Integer bonusStatus) {
            this.bonusStatus = bonusStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getRenewalNo() {
            return renewalNo;
        }

        public void setRenewalNo(Integer renewalNo) {
            this.renewalNo = renewalNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getPackAmt() {
            return packAmt;
        }

        public void setPackAmt(Integer packAmt) {
            this.packAmt = packAmt;
        }

    }
}
