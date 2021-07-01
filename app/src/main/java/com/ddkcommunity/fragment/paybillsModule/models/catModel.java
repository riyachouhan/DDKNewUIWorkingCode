package com.ddkcommunity.fragment.paybillsModule.models;

import com.ddkcommunity.model.smpdModelNew;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class catModel implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data_category")
    @Expose
    private List<DataCategory> dataCategory = null;
    @SerializedName("data_biller")
    @Expose
    private List<DataBiller> dataBiller = null;
    private final static long serialVersionUID = 8118365522457061624L;

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

    public List<DataCategory> getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(List<DataCategory> dataCategory) {
        this.dataCategory = dataCategory;
    }

    public List<DataBiller> getDataBiller() {
        return dataBiller;
    }

    public void setDataBiller(List<DataBiller> dataBiller) {
        this.dataBiller = dataBiller;
    }

    public static class DataCategory implements Serializable
        {

            @SerializedName("category_name")
            @Expose
            private String categoryName;
            @SerializedName("category_image")
            @Expose
            private String categoryImage;
            private final static long serialVersionUID = 5751119984909473068L;

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getCategoryImage() {
                return categoryImage;
            }

            public void setCategoryImage(String categoryImage) {
                this.categoryImage = categoryImage;
            }

        }

        public static class DataBiller implements Serializable
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
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("deleted_at")
            @Expose
            private Object deletedAt;
            @SerializedName("deleted")
            @Expose
            private String deleted;
            @SerializedName("status")
            @Expose
            private String status;
            private final static long serialVersionUID = 8811269715969697056L;

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

            public Object getDeletedAt() {
                return deletedAt;
            }

            public void setDeletedAt(Object deletedAt) {
                this.deletedAt = deletedAt;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }
}
