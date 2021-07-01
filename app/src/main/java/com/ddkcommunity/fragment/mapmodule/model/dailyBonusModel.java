package com.ddkcommunity.fragment.mapmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class dailyBonusModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -482248357661295742L;

    public List<Datum> getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("package_id")
        @Expose
        private Integer packageId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("bonus_type")
        @Expose
        private Object bonusType;
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
        @SerializedName("last_id_package")
        @Expose
        private Integer lastIdPackage;
        @SerializedName("plan_id")
        @Expose
        private Integer planId;
        @SerializedName("package_name")
        @Expose
        private String packageName;
        private final static long serialVersionUID = -217275973698387521L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Object getBonusType() {
            return bonusType;
        }

        public void setBonusType(Object bonusType) {
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

        public Integer getLastIdPackage() {
            return lastIdPackage;
        }

        public void setLastIdPackage(Integer lastIdPackage) {
            this.lastIdPackage = lastIdPackage;
        }

        public Integer getPlanId() {
            return planId;
        }

        public void setPlanId(Integer planId) {
            this.planId = planId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

    }
}
