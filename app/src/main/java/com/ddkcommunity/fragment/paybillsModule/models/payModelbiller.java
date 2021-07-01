package com.ddkcommunity.fragment.paybillsModule.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class payModelbiller implements Serializable
{
    public payModelbiller(Integer id, String name) {
        Id = id;
        Name = name;
    }

    private Integer Id;
    private String Name;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
