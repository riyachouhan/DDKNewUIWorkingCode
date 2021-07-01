package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class currencyModelClick implements Serializable {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("result")
    @Expose
    private String result;
    private final static long serialVersionUID = -964598827433336290L;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public class Info implements Serializable {

        @SerializedName("timestamp")
        @Expose
        private Integer timestamp;
        @SerializedName("rate")
        @Expose
        private Double rate;
        private final static long serialVersionUID = -4153855396314620225L;

        public Integer getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
        }

        public Double getRate() {
            return rate;
        }

        public void setRate(Double rate) {
            this.rate = rate;
        }

    }

    public class Query implements Serializable {

        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        private final static long serialVersionUID = 2189283670047886740L;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

    }

}