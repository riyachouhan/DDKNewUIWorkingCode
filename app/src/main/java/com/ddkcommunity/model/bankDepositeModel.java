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


    public class Datum implements Serializable
    {

        @SerializedName("bank_detail_id")
        @Expose
        private Integer bankDetailId;
        @SerializedName("bank_name_label")
        @Expose
        private String bankNameLabel;
        @SerializedName("bank_name")
        @Expose
        private String bankName;
        @SerializedName("account_number_label")
        @Expose
        private String accountNumberLabel;
        @SerializedName("account_number")
        @Expose
        private String accountNumber;
        @SerializedName("account_name_label")
        @Expose
        private String accountNameLabel;
        @SerializedName("account_name")
        @Expose
        private String accountName;
        @SerializedName("swift_code_label")
        @Expose
        private String swiftCodeLabel;
        @SerializedName("swift_code")
        @Expose
        private String swiftCode;
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
        private Object updatedAt;
        private final static long serialVersionUID = 3804126950908493059L;

        public Integer getBankDetailId() {
            return bankDetailId;
        }

        public void setBankDetailId(Integer bankDetailId) {
            this.bankDetailId = bankDetailId;
        }

        public String getBankNameLabel() {
            return bankNameLabel;
        }

        public void setBankNameLabel(String bankNameLabel) {
            this.bankNameLabel = bankNameLabel;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getAccountNumberLabel() {
            return accountNumberLabel;
        }

        public void setAccountNumberLabel(String accountNumberLabel) {
            this.accountNumberLabel = accountNumberLabel;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountNameLabel() {
            return accountNameLabel;
        }

        public void setAccountNameLabel(String accountNameLabel) {
            this.accountNameLabel = accountNameLabel;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getSwiftCodeLabel() {
            return swiftCodeLabel;
        }

        public void setSwiftCodeLabel(String swiftCodeLabel) {
            this.swiftCodeLabel = swiftCodeLabel;
        }

        public String getSwiftCode() {
            return swiftCode;
        }

        public void setSwiftCode(String swiftCode) {
            this.swiftCode = swiftCode;
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

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
