package com.ddkcommunity.fragment.settingModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ARPDirectModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -404704854851201167L;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum implements Serializable
    {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("points")
        @Expose
        private String points;
        @SerializedName("is_transferred")
        @Expose
        private String is_transferred;

        public String getIs_transferred() {
            return is_transferred;
        }

        public void setIs_transferred(String is_transferred) {
            this.is_transferred = is_transferred;
        }

        private final static long serialVersionUID = 7198102467545730237L;

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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

    }
}
