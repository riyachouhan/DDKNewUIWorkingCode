package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class bankDepositeModel implements Serializable
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

    public class Bankfield implements Serializable
    {

        @SerializedName("detail_id")
        @Expose
        private Integer detailId;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("bank_detail_id")
        @Expose
        private Integer bankDetailId;
        @SerializedName("value")
        @Expose
        private String value;
        private final static long serialVersionUID = 8679234626721910054L;

        public Integer getDetailId() {
            return detailId;
        }

        public void setDetailId(Integer detailId) {
            this.detailId = detailId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getBankDetailId() {
            return bankDetailId;
        }

        public void setBankDetailId(Integer bankDetailId) {
            this.bankDetailId = bankDetailId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public class Datum implements Serializable
    {

        @SerializedName("bank_detail_id")
        @Expose
        private Integer bankDetailId;
        @SerializedName("Bankfield")
        @Expose
        private List<Bankfield> bankfield = null;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        private final static long serialVersionUID = 3278784714649686968L;

        public Integer getBankDetailId() {
            return bankDetailId;
        }

        public void setBankDetailId(Integer bankDetailId) {
            this.bankDetailId = bankDetailId;
        }

        public List<Bankfield> getBankfield() {
            return bankfield;
        }

        public void setBankfield(List<Bankfield> bankfield) {
            this.bankfield = bankfield;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

    }
}
