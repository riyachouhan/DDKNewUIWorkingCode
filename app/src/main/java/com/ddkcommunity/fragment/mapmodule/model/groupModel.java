package com.ddkcommunity.fragment.mapmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class groupModel implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("left_group")
    @Expose
    private List<LeftGroup> leftGroup = null;
    @SerializedName("right_group")
    @Expose
    private List<RightGroup> rightGroup = null;
    @SerializedName("left_balance")
    @Expose
    private Integer leftBalance;
    @SerializedName("right_balance")
    @Expose
    private Integer rightBalance;
    @SerializedName("left_count_user")
    @Expose
    private Integer leftCountUser;
    @SerializedName("right_count_user")
    @Expose
    private Integer rightCountUser;
    private final static long serialVersionUID = 1943497327741112866L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<LeftGroup> getLeftGroup() {
        return leftGroup;
    }

    public void setLeftGroup(List<LeftGroup> leftGroup) {
        this.leftGroup = leftGroup;
    }

    public List<RightGroup> getRightGroup() {
        return rightGroup;
    }

    public void setRightGroup(List<RightGroup> rightGroup) {
        this.rightGroup = rightGroup;
    }

    public Integer getLeftBalance() {
        return leftBalance;
    }

    public void setLeftBalance(Integer leftBalance) {
        this.leftBalance = leftBalance;
    }

    public Integer getRightBalance() {
        return rightBalance;
    }

    public void setRightBalance(Integer rightBalance) {
        this.rightBalance = rightBalance;
    }

    public Integer getLeftCountUser() {
        return leftCountUser;
    }

    public void setLeftCountUser(Integer leftCountUser) {
        this.leftCountUser = leftCountUser;
    }

    public Integer getRightCountUser() {
        return rightCountUser;
    }

    public void setRightCountUser(Integer rightCountUser) {
        this.rightCountUser = rightCountUser;
    }

    public class LeftGroup implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_from")
        @Expose
        private String userFrom;
        @SerializedName("ref_id")
        @Expose
        private Integer refId;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("position")
        @Expose
        private Integer position;
        @SerializedName("ref_code")
        @Expose
        private String refCode;
        @SerializedName("plan_id")
        @Expose
        private Integer planId;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("otp")
        @Expose
        private Object otp;
        @SerializedName("otp_expire_date")
        @Expose
        private Object otpExpireDate;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("address")
        @Expose
        private Address address;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("ev")
        @Expose
        private Integer ev;
        @SerializedName("sv")
        @Expose
        private Integer sv;
        @SerializedName("ver_code")
        @Expose
        private String verCode;
        @SerializedName("ver_code_send_at")
        @Expose
        private String verCodeSendAt;
        @SerializedName("ts")
        @Expose
        private Integer ts;
        @SerializedName("tv")
        @Expose
        private Integer tv;
        @SerializedName("tsc")
        @Expose
        private Object tsc;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("pack_amt")
        @Expose
        private Integer packAmt;
        @SerializedName("package_status")
        @Expose
        private String packageStatus;
        @SerializedName("renewal_no")
        @Expose
        private String renewalNo;
        private final static long serialVersionUID = 4436207572565049415L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserFrom() {
            return userFrom;
        }

        public void setUserFrom(String userFrom) {
            this.userFrom = userFrom;
        }

        public Integer getRefId() {
            return refId;
        }

        public void setRefId(Integer refId) {
            this.refId = refId;
        }

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getRefCode() {
            return refCode;
        }

        public void setRefCode(String refCode) {
            this.refCode = refCode;
        }

        public Integer getPlanId() {
            return planId;
        }

        public void setPlanId(Integer planId) {
            this.planId = planId;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getOtp() {
            return otp;
        }

        public void setOtp(Object otp) {
            this.otp = otp;
        }

        public Object getOtpExpireDate() {
            return otpExpireDate;
        }

        public void setOtpExpireDate(Object otpExpireDate) {
            this.otpExpireDate = otpExpireDate;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getEv() {
            return ev;
        }

        public void setEv(Integer ev) {
            this.ev = ev;
        }

        public Integer getSv() {
            return sv;
        }

        public void setSv(Integer sv) {
            this.sv = sv;
        }

        public String getVerCode() {
            return verCode;
        }

        public void setVerCode(String verCode) {
            this.verCode = verCode;
        }

        public String getVerCodeSendAt() {
            return verCodeSendAt;
        }

        public void setVerCodeSendAt(String verCodeSendAt) {
            this.verCodeSendAt = verCodeSendAt;
        }

        public Integer getTs() {
            return ts;
        }

        public void setTs(Integer ts) {
            this.ts = ts;
        }

        public Integer getTv() {
            return tv;
        }

        public void setTv(Integer tv) {
            this.tv = tv;
        }

        public Object getTsc() {
            return tsc;
        }

        public void setTsc(Object tsc) {
            this.tsc = tsc;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

        public Integer getPackAmt() {
            return packAmt;
        }

        public void setPackAmt(Integer packAmt) {
            this.packAmt = packAmt;
        }

        public String getPackageStatus() {
            return packageStatus;
        }

        public void setPackageStatus(String packageStatus) {
            this.packageStatus = packageStatus;
        }

        public String getRenewalNo() {
            return renewalNo;
        }

        public void setRenewalNo(String renewalNo) {
            this.renewalNo = renewalNo;
        }
    }

    public class Address implements Serializable
    {

        @SerializedName("country")
        @Expose
        private String country;
        private final static long serialVersionUID = -8759175479959879034L;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public class Address__1 implements Serializable
    {

        @SerializedName("country")
        @Expose
        private String country;
        private final static long serialVersionUID = -115844644097351423L;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public class RightGroup implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_from")
        @Expose
        private String userFrom;
        @SerializedName("ref_id")
        @Expose
        private Integer refId;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("position")
        @Expose
        private Integer position;
        @SerializedName("ref_code")
        @Expose
        private String refCode;
        @SerializedName("plan_id")
        @Expose
        private Integer planId;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("otp")
        @Expose
        private Object otp;
        @SerializedName("otp_expire_date")
        @Expose
        private Object otpExpireDate;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("address")
        @Expose
        private Address__1 address;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("ev")
        @Expose
        private Integer ev;
        @SerializedName("sv")
        @Expose
        private Integer sv;
        @SerializedName("ver_code")
        @Expose
        private Object verCode;
        @SerializedName("ver_code_send_at")
        @Expose
        private Object verCodeSendAt;
        @SerializedName("ts")
        @Expose
        private Integer ts;
        @SerializedName("tv")
        @Expose
        private Integer tv;
        @SerializedName("tsc")
        @Expose
        private Object tsc;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("pack_amt")
        @Expose
        private Integer packAmt;
        @SerializedName("package_status")
        @Expose
        private String packageStatus;
        @SerializedName("renewal_no")
        @Expose
        private Integer renewalNo;
        private final static long serialVersionUID = -1076072266710194849L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserFrom() {
            return userFrom;
        }

        public void setUserFrom(String userFrom) {
            this.userFrom = userFrom;
        }

        public Integer getRefId() {
            return refId;
        }

        public void setRefId(Integer refId) {
            this.refId = refId;
        }

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getRefCode() {
            return refCode;
        }

        public void setRefCode(String refCode) {
            this.refCode = refCode;
        }

        public Integer getPlanId() {
            return planId;
        }

        public void setPlanId(Integer planId) {
            this.planId = planId;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getOtp() {
            return otp;
        }

        public void setOtp(Object otp) {
            this.otp = otp;
        }

        public Object getOtpExpireDate() {
            return otpExpireDate;
        }

        public void setOtpExpireDate(Object otpExpireDate) {
            this.otpExpireDate = otpExpireDate;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Address__1 getAddress() {
            return address;
        }

        public void setAddress(Address__1 address) {
            this.address = address;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getEv() {
            return ev;
        }

        public void setEv(Integer ev) {
            this.ev = ev;
        }

        public Integer getSv() {
            return sv;
        }

        public void setSv(Integer sv) {
            this.sv = sv;
        }

        public Object getVerCode() {
            return verCode;
        }

        public void setVerCode(Object verCode) {
            this.verCode = verCode;
        }

        public Object getVerCodeSendAt() {
            return verCodeSendAt;
        }

        public void setVerCodeSendAt(Object verCodeSendAt) {
            this.verCodeSendAt = verCodeSendAt;
        }

        public Integer getTs() {
            return ts;
        }

        public void setTs(Integer ts) {
            this.ts = ts;
        }

        public Integer getTv() {
            return tv;
        }

        public void setTv(Integer tv) {
            this.tv = tv;
        }

        public Object getTsc() {
            return tsc;
        }

        public void setTsc(Object tsc) {
            this.tsc = tsc;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

        public Integer getPackAmt() {
            return packAmt;
        }

        public void setPackAmt(Integer packAmt) {
            this.packAmt = packAmt;
        }

        public String getPackageStatus() {
            return packageStatus;
        }

        public void setPackageStatus(String packageStatus) {
            this.packageStatus = packageStatus;
        }

        public Integer getRenewalNo() {
            return renewalNo;
        }

        public void setRenewalNo(Integer renewalNo) {
            this.renewalNo = renewalNo;
        }

    }
}