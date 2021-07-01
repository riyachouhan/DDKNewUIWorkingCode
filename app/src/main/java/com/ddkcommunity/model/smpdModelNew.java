package com.ddkcommunity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class smpdModelNew implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("favourites")
    @Expose
    private List<Favourite> favourites = null;
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;
    @SerializedName("msg")
    @Expose
    private String msg;
    private final static long serialVersionUID = -162542114025071877L;

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

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Favourite implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sampd_company_id")
        @Expose
        private Integer sampdCompanyId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("sampd_company")
        @Expose
        private SampdCompany sampdCompany;
        private final static long serialVersionUID = -697521394312124004L;

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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public SampdCompany getSampdCompany() {
            return sampdCompany;
        }

        public void setSampdCompany(SampdCompany sampdCompany) {
            this.sampdCompany = sampdCompany;
        }

    }

    public static class  SampdCompany implements Serializable
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
        private Object updatedAt;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        private final static long serialVersionUID = -2062419131300761393L;

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

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

    }

    public static class Datum implements Serializable
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

        @SerializedName("comapany_profile_view_type")
        @Expose
        private String comapany_profile_view_type;

        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("is_favourite")
        @Expose
        private Integer isFavourite;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        private final static long serialVersionUID = 168169402656397947L;

        public String getComapany_profile_view_type() {
            return comapany_profile_view_type;
        }

        public void setComapany_profile_view_type(String comapany_profile_view_type) {
            this.comapany_profile_view_type = comapany_profile_view_type;
        }

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

    }

    public static class Banner implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("banner_type")
        @Expose
        private String bannerType;
        @SerializedName("is_active")
        @Expose
        private Integer isActive;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        private final static long serialVersionUID = -4754194072413158942L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getBannerType() {
            return bannerType;
        }

        public void setBannerType(String bannerType) {
            this.bannerType = bannerType;
        }

        public Integer getIsActive() {
            return isActive;
        }

        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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
