package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SMPDCompanyDetailsModel implements Serializable
{

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

    public class SampdCompanyProfile implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sampd_company_id")
        @Expose
        private Integer sampdCompanyId;
        @SerializedName("profile_lable")
        @Expose
        private String profileLable;
        @SerializedName("profile_description")
        @Expose
        private String profileDescription;
        @SerializedName("contact_engagement_lable")
        @Expose
        private String contactEngagementLable;
        @SerializedName("contact_engagement_description")
        @Expose
        private String contactEngagementDescription;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        private final static long serialVersionUID = -9115150043611621666L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSampdCompanyId() {
            return sampdCompanyId;
        }

        public void setSampdCompanyId(Integer sampdCompanyId) {
            this.sampdCompanyId = sampdCompanyId;
        }

        public String getProfileLable() {
            return profileLable;
        }

        public void setProfileLable(String profileLable) {
            this.profileLable = profileLable;
        }

        public String getProfileDescription() {
            return profileDescription;
        }

        public void setProfileDescription(String profileDescription) {
            this.profileDescription = profileDescription;
        }

        public String getContactEngagementLable() {
            return contactEngagementLable;
        }

        public void setContactEngagementLable(String contactEngagementLable) {
            this.contactEngagementLable = contactEngagementLable;
        }

        public String getContactEngagementDescription() {
            return contactEngagementDescription;
        }

        public void setContactEngagementDescription(String contactEngagementDescription) {
            this.contactEngagementDescription = contactEngagementDescription;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Data implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon_image")
        @Expose
        private String iconImage;
        @SerializedName("sorting")
        @Expose
        private Integer sorting;
        @SerializedName("country_id")
        @Expose
        private Object countryId;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("is_favourite")
        @Expose
        private Integer isFavourite;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("sampd_company_services")
        @Expose
        private List<SampdCompanyService> sampdCompanyServices = null;
        @SerializedName("sampd_company_profile")
        @Expose
        private SampdCompanyProfile sampdCompanyProfile;
        private final static long serialVersionUID = -8302630351221055487L;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconImage() {
            return iconImage;
        }

        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

        public Integer getSorting() {
            return sorting;
        }

        public void setSorting(Integer sorting) {
            this.sorting = sorting;
        }

        public Object getCountryId() {
            return countryId;
        }

        public void setCountryId(Object countryId) {
            this.countryId = countryId;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getIsFavourite() {
            return isFavourite;
        }

        public void setIsFavourite(Integer isFavourite) {
            this.isFavourite = isFavourite;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public List<SampdCompanyService> getSampdCompanyServices() {
            return sampdCompanyServices;
        }

        public void setSampdCompanyServices(List<SampdCompanyService> sampdCompanyServices) {
            this.sampdCompanyServices = sampdCompanyServices;
        }

        public SampdCompanyProfile getSampdCompanyProfile() {
            return sampdCompanyProfile;
        }

        public void setSampdCompanyProfile(SampdCompanyProfile sampdCompanyProfile) {
            this.sampdCompanyProfile = sampdCompanyProfile;
        }

    }

    public class SampdCompanyService implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sampd_company_id")
        @Expose
        private Integer sampdCompanyId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        private final static long serialVersionUID = -2372383645024532036L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSampdCompanyId() {
            return sampdCompanyId;
        }

        public void setSampdCompanyId(Integer sampdCompanyId) {
            this.sampdCompanyId = sampdCompanyId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
