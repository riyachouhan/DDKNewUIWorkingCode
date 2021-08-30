package com.ddkcommunity.fragment.mapmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class direcetBidModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("extra_data")
    @Expose
    private List<ExtraDatum> extraData = null;
    private final static long serialVersionUID = 8765539457359158002L;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ExtraDatum> getExtraData() {
        return extraData;
    }

    public void setExtraData(List<ExtraDatum> extraData) {
        this.extraData = extraData;
    }

    public class ExtraDatum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private String  id;
        @SerializedName("pos_id")
        @Expose
        private String  posId;
        @SerializedName("position")
        @Expose
        private String  position;
        @SerializedName("is_purchased")
        @Expose
        private int isPurchased;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("purchased_at")
        @Expose
        private Object purchasedAt;
        @SerializedName("last_amount")
        @Expose
        private String last_amount;
        @SerializedName("remarks")
        @Expose
        private Object remarks;
        private final static long serialVersionUID = 8521150651346397204L;

        public String getLast_amount() {
            return last_amount;
        }

        public void setLast_amount(String last_amount) {
            this.last_amount = last_amount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosId() {
            return posId;
        }

        public void setPosId(String posId) {
            this.posId = posId;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getIsPurchased() {
            return isPurchased;
        }

        public void setIsPurchased(int isPurchased) {
            this.isPurchased = isPurchased;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getPurchasedAt() {
            return purchasedAt;
        }

        public void setPurchasedAt(Object purchasedAt) {
            this.purchasedAt = purchasedAt;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

    }
}
