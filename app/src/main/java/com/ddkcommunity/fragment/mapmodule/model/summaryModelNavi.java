package com.ddkcommunity.fragment.mapmodule.model;

import com.ddkcommunity.model.navigationModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class summaryModelNavi implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("package")
    @Expose
    private Package _package;
    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = 932918800035614190L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Package getPackage() {
        return _package;
    }

    public void setPackage(Package _package) {
        this._package = _package;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable
        {

            @SerializedName("direct_bonus")
            @Expose
            private Double directBonus;
            @SerializedName("power_bonus")
            @Expose
            private Double powerBonus;
            @SerializedName("group_bonus")
            @Expose
            private Double groupBonus;
            @SerializedName("platinum_bonus")
            @Expose
            private Double platinumBonus;
            @SerializedName("titanium_bonus")
            @Expose
            private Double titaniumBonus;
            @SerializedName("daily_bonus")
            @Expose
            private Double dailyBonus;

            public Double getDirectBonus() {
                return directBonus;
            }

            public void setDirectBonus(Double directBonus) {
                this.directBonus = directBonus;
            }

            public Double getPowerBonus() {
                return powerBonus;
            }

            public void setPowerBonus(Double powerBonus) {
                this.powerBonus = powerBonus;
            }

            public Double getGroupBonus() {
                return groupBonus;
            }

            public void setGroupBonus(Double groupBonus) {
                this.groupBonus = groupBonus;
            }

            public Double getPlatinumBonus() {
                return platinumBonus;
            }

            public void setPlatinumBonus(Double platinumBonus) {
                this.platinumBonus = platinumBonus;
            }

            public Double getTitaniumBonus() {
                return titaniumBonus;
            }

            public void setTitaniumBonus(Double titaniumBonus) {
                this.titaniumBonus = titaniumBonus;
            }

            public Double getDailyBonus() {
                return dailyBonus;
            }

            public void setDailyBonus(Double dailyBonus) {
                this.dailyBonus = dailyBonus;
            }
        }

        public class Package implements Serializable
        {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("pack_amt")
            @Expose
            private Integer packAmt;
            private final static long serialVersionUID = -7157955883216784381L;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getPackAmt() {
                return packAmt;
            }

            public void setPackAmt(Integer packAmt) {
                this.packAmt = packAmt;
            }

        }
}
