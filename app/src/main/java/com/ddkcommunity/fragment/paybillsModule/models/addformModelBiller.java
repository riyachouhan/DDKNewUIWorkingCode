package com.ddkcommunity.fragment.paybillsModule.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class addformModelBiller implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    private final static long serialVersionUID = -4474516099102627109L;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Value implements Serializable
    {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private String value;
        private final static long serialVersionUID = 7128618269631461022L;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public static class Datum implements Serializable
    {

        @SerializedName("validation")
        @Expose
        private String validation;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sample")
        @Expose
        private String sample;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("type")
        @Expose
        private String type;
        /*@SerializedName("value")
        @Expose
        private List<Value> value = null;*/

        @SerializedName("value")
        @Expose
        private String value;

        @SerializedName("default")
        @Expose
        private String _default;
        @SerializedName("json_sub_type")
        @Expose
        private String jsonSubType;
        @SerializedName("json_type")
        @Expose
        private String jsonType;

        @SerializedName("validation_msg")
        @Expose
        private String validation_msg;

        private final static long serialVersionUID = -5315535826530651077L;

        public String getValidation() {
            return validation;
        }

        public void setValidation(String validation) {
            this.validation = validation;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        /* public List<Value> getValue() {
                    return value;
                }

                public void setValue(List<Value> value) {
                    this.value = value;
                }
        */
        public String getDefault() {
            return _default;
        }

        public String get_default() {
            return _default;
        }

        public void set_default(String _default) {
            this._default = _default;
        }

        public String getValidation_msg() {
            return validation_msg;
        }

        public void setValidation_msg(String validation_msg) {
            this.validation_msg = validation_msg;
        }

        public void setDefault(String _default) {
            this._default = _default;
        }

        public String getJsonType() {
            return jsonType;
        }

        public void setJsonType(String jsonType) {
            this.jsonType = jsonType;
        }

        public String getJsonSubType() {
            return jsonSubType;
        }

        public void setJsonSubType(String jsonSubType) {
            this.jsonSubType = jsonSubType;
        }

    }
}
