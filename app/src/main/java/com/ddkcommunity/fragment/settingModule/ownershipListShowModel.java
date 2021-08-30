package com.ddkcommunity.fragment.settingModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ownershipListShowModel implements Serializable {

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

    public class Data implements Serializable {

        @SerializedName("ownership_data")
        @Expose
        private OwnershipData ownershipData;
        @SerializedName("ownership_list")
        @Expose
        private List<Ownership> ownershipList = null;
        private final static long serialVersionUID = -7405856087139555911L;

        public OwnershipData getOwnershipData() {
            return ownershipData;
        }

        public void setOwnershipData(OwnershipData ownershipData) {
            this.ownershipData = ownershipData;
        }

        public List<Ownership> getOwnershipList() {
            return ownershipList;
        }

        public void setOwnershipList(List<Ownership> ownershipList) {
            this.ownershipList = ownershipList;
        }

    }

    public class Ownership implements Serializable {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("trx_number")
        @Expose
        private String trxNumber;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("ownership_type")
        @Expose
        private String ownershipType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("bank_status")
        @Expose
        private String bankStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        private final static long serialVersionUID = -2276183901757895415L;

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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getOwnershipType() {
            return ownershipType;
        }

        public void setOwnershipType(String ownershipType) {
            this.ownershipType = ownershipType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBankStatus() {
            return bankStatus;
        }

        public void setBankStatus(String bankStatus) {
            this.bankStatus = bankStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

    public class OwnershipData implements Serializable {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        private final static long serialVersionUID = 1584589960912037391L;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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
