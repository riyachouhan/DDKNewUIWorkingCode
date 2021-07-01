package com.ddkcommunity.fragment.mapmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class overFlowModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("total_overflow_points")
    @Expose
    private Integer totalOverflowPoints;
    private final static long serialVersionUID = 5402200811675255436L;

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

    public Integer getTotalOverflowPoints() {
        return totalOverflowPoints;
    }

    public void setTotalOverflowPoints(Integer totalOverflowPoints) {
        this.totalOverflowPoints = totalOverflowPoints;
    }

    public class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("bonus_type")
        @Expose
        private String bonusType;
        @SerializedName("points")
        @Expose
        private String points;
        @SerializedName("overflow_expiration_date")
        @Expose
        private String overflowExpirationDate;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("renewal_no")
        @Expose
        private String renewal_no;

        public String getRenewal_no() {
            return renewal_no;
        }

        public void setRenewal_no(String renewal_no) {
            this.renewal_no = renewal_no;
        }

        private final static long serialVersionUID = 2994814388176820569L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getOverflowExpirationDate() {
            return overflowExpirationDate;
        }

        public void setOverflowExpirationDate(String overflowExpirationDate) {
            this.overflowExpirationDate = overflowExpirationDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
