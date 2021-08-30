package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class emergencyModel implements Serializable
    {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("msg")
        @Expose
        private String msg;

        private final static long serialVersionUID = -3406141666563551663L;

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

}
