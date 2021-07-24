package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class adsDialogModel implements Serializable {

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

        @SerializedName("ad_image")
        @Expose
        private String adImage;
        @SerializedName("ad_url")
        @Expose
        private String adUrl;
        @SerializedName("ad_status")
        @Expose
        private String adStatus;
        private final static long serialVersionUID = 2451621771481023834L;

        public String getAdImage() {
            return adImage;
        }

        public void setAdImage(String adImage) {
            this.adImage = adImage;
        }

        public String getAdUrl() {
            return adUrl;
        }

        public void setAdUrl(String adUrl) {
            this.adUrl = adUrl;
        }

        public String getAdStatus() {
            return adStatus;
        }

        public void setAdStatus(String adStatus) {
            this.adStatus = adStatus;
        }

    }
}