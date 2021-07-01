package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class sfioAddModelsee implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = -4078616553802259260L;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable
    {

        @SerializedName("txt_id")
        @Expose
        private String txtId;
        @SerializedName("lender_id")
        @Expose
        private String lenderId;
        private final static long serialVersionUID = -5192794335577137263L;

        public String getTxtId() {
            return txtId;
        }

        public void setTxtId(String txtId) {
            this.txtId = txtId;
        }

        public String getLenderId() {
            return lenderId;
        }

        public void setLenderId(String lenderId) {
            this.lenderId = lenderId;
        }

    }

}
